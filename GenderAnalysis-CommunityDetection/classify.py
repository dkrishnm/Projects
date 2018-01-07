"""
classify.py
"""
import pickle
import re
import numpy as np
from itertools import chain, combinations
from sklearn.cross_validation import KFold
from sklearn.linear_model import LogisticRegression
from collections import Counter, defaultdict
from sklearn.neighbors import KNeighborsClassifier
from scipy.sparse import lil_matrix

def tokenize(doc, keep_internal_punct=False):
    token = []
    if (keep_internal_punct):
        token = re.sub(r'(?<!\S)[^\s\w]+|[^\s\w]+(?!\S)', ' ', str(doc).lower()).split()
    else:
        token = re.sub('\W+', ' ', doc.lower()).split()
    return np.array(token)

def featurize(tokens, feature_fns):
    feats = defaultdict(lambda: 0)
    for feature in feature_fns:
        feature(tokens, feats)
    return sorted(feats.items())

def make_feature_matrix(tokens_list, vocabulary):
    X = lil_matrix((len(tokens_list), len(vocabulary)))
    for i, tokens in enumerate(tokens_list):
        for token in tokens:
            if token in vocabulary.keys():
                j = vocabulary[token]
                X[i, j] += 1
    return X.tocsr()

def make_vocabulary(tokens_list):
    vocabulary = defaultdict(lambda: len(vocabulary))
    for tokens in tokens_list:
        for token in tokens:
            vocabulary[token]
    return vocabulary

def vectorize(tokens_list, feature_fns, min_freq, vocab=None):
    feature_list = []
    termCount = {}
    for token in tokens_list:
        features = featurize(token, feature_fns)
        feature_list.append(features)
        for feature in features:
            if feature[0] in termCount:
                termCount[feature[0]]+= 1
            else:
                termCount[feature[0]] = 1
    if (vocab == None):
        vocab = make_vocabulary(tokens_list)
    X = make_feature_matrix(tokens_list, vocab)
    return X, vocab


def token_features(tokens, features):
    token_list = []
    for token in tokens:
        token_list.append('token=' + token)
    count = Counter(token_list)
    for key, value in count.items():
        features[key] += value


def token_pair_features(tokens, feats, k=3):
    tokenList = []
    for i in range(len(tokens)):
        windows = []
        for j in range(i, i + k):
            if (j < len(tokens)):
                windows.append(tokens[j])
        if (len(windows) == k):
            subWindows = list(combinations(windows, 2))
            for subWindow in subWindows:
                value = subWindow[0] + '__' + subWindow[1]
                tokenList.append(value)
    tpList = []
    for tweet in tokenList:
        tokenKey = 'token_pair=' + tweet
        tpList.append(tokenKey)
    count = Counter(tpList)
    for key, value in count.items():
        feats[key] = feats[key] + value


def accuracy_score(truth, predicted):
    return len(np.where(truth == predicted)[0]) / len(truth)


def cross_validation_accuracy(clf, X, labels, k):
    cv = KFold(len(labels), k)
    accuracies = []
    for train_ind, test_ind in cv:
        clf.fit(X[train_ind], labels[train_ind])
        predictions = clf.predict(X[test_ind])
        accuracies.append(accuracy_score(labels[test_ind], predictions))
    return np.mean(accuracies)


def getUnknownFollowers(followers):
    docs = []
    count = 0
    userList = []
    for userName in followers.keys():
        if ('gender' in followers[userName]):
            if (followers[userName]['gender'] == 'unknown'):
                docs.append(followers[userName]['description'])
                if len(followers[userName]['description']) == 0:
                    count+=1
                userList.append(userName)
    return docs, userList


def predictGender(result, unknowns, docs, labels, followers, users):
    internal_punct = result['punct']
    combinations = result['features']
    male_count = 0
    female_count = 0
    freq = result['min_freq']
    docList = []
    unknownDocs = []
    for doc in docs:
        docList.append(tokenize(doc, internal_punct))
    matrix, vocab = vectorize(docList, combinations, freq)
    model = LogisticRegression()
    model.fit(matrix, labels)
    for doc in unknowns:
        unknownDocs.append(tokenize(doc, internal_punct))
    unknown_csr, unknown_vocab = vectorize(unknownDocs, combinations, freq, vocab)
    predictions = model.predict(unknown_csr)
    for user, prediction in zip(users, predictions):
        data = followers[user]
        if prediction == 0:
            data['gender'] = 'male'
            male_count+=1
        else:
            data['gender'] = 'female'
            female_count += 1
        followers[user] = data

def get_gender_byname(followers, male_names, female_names):
    female_count=0
    male_count=0
    unknown_count =0
    for uname in followers.keys():
        user_detail = followers[uname]
        name = user_detail['name']
        if name:
            name_parts = re.findall('\w+', name.split()[0].lower())
            if len(name_parts) > 0:
                first = name_parts[0].lower()
                if first in male_names:
                    user_detail['gender'] = 'male'
                    male_count+= 1
                elif first in female_names:
                    user_detail['gender'] = 'female'
                    female_count+= 1
                else:
                    user_detail['gender'] = 'unknown'
                    unknown_count+= 1
                followers[uname] = user_detail

def get_docs(followers):
    count = 0
    docs = []
    truth = []
    for userName in followers.keys():
        if ('gender' in followers[userName]):
            if (followers[userName]['gender'] != 'unknown'):
                docs.append(followers[userName]['description'])
                if len(followers[userName]['description']) == 0:
                    count = count + 1
                if followers[userName]['gender'] != 'male':
                    truth.append(1)
                else:
                    truth.append(0)
    return docs, np.array(truth)

def eval_all_combinations(docs, labels, punct_vals,feature_fns, min_freqs):
    result = []
    feature_fns_list = []
    combinationlist = []
    for i in range(1, len(feature_fns) + 1):
        combinationlist.append([list(j) for j in combinations(feature_fns, i)])
    for combination in combinationlist:
        for comb in combination:
            feature_fns_list.append(comb)
    for punctval in punct_vals:
        tokenlist = []
        for doc in docs:
            tk = tokenize(doc, punctval)
            tokenlist.append(tk)
        for feature in feature_fns_list:
            for freq in min_freqs:
                matrix, vocab = vectorize(tokenlist, feature, freq)
                accuracy = cross_validation_accuracy(LogisticRegression(), matrix, labels, 5)
                result.append({'features': feature, 'punct': punctval, 'accuracy': accuracy, 'min_freq': freq})
    sortedList = sorted(result, key=lambda k: -k['accuracy'])
    return sortedList

def main():
    male_names = pickle.load(open('male_names.pkl', 'rb'))
    female_names = pickle.load(open('female_names.pkl', 'rb'))
    followerDetails = pickle.load(open('followers.pkl', 'rb'))
    get_gender_byname(followerDetails, male_names, female_names)
    docs,labels = get_docs(followerDetails)
    feature_fns = [token_features, token_pair_features]
    results = eval_all_combinations(docs, labels,[True, False],feature_fns,[2, 5, 10])
    best_result = results[0]
    worst_result = results[-1]
    print('best cross-validation result:\n%s' % str(best_result))
    print('worst cross-validation result:\n%s' % str(worst_result))
    unknownGender, userList = getUnknownFollowers(followerDetails)
    predictGender(best_result, unknownGender, docs, labels, followerDetails, userList)
    pickle.dump(followerDetails, open('followerdetails.pkl', 'wb'))


if __name__ == '__main__':
    main()
