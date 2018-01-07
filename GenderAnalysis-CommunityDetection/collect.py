"""
collect.py
"""
from TwitterAPI import TwitterAPI
import sys
import time
import requests
import pickle

consumer_key = ''
consumer_secret = ''
access_token = ''
access_token_secret = ''

def get_twitter():
    return TwitterAPI(consumer_key, consumer_secret, access_token, access_token_secret)

def robust_request(twitter, resource, params, max_tries=5):
    for i in range(max_tries):
        request = twitter.request(resource, params)
        if request.status_code == 200:
            return request
        else:
            print('Got error %s \nsleeping for 15 minutes.' % request.text)
            sys.stderr.flush()
            time.sleep(61 * 15)

def read_screen_names(filename):
    file = open(filename)
    return [line.strip() for line in file]
def get_users(twitter, screen_names):
    params = {'screen_name' : screen_names}
    return robust_request(twitter, 'users/lookup', params)

def get_followers(twitter, screen_name):
    followerDetails = {}
    request = robust_request(twitter, 'followers/list', {'screen_name': screen_name, 'count': 100})
    followers = []
    followers_desc =0
    for r in request:
        length = len(r['description'])
        if (length > 1):
            followers.append(r['screen_name'])
        else:
           followers_desc+=1
    followersList = list(followers)
    for r in request:
        length = len(r['description'])
        if (length > 1):
            dict = {}
            dict['description'] = r['description']
            dict['name'] = r['name']
            dict['screen_name'] = r['screen_name']
            followerDetails[r['screen_name']] = dict
    #print (followers_desc)
    return followersList, followerDetails

def get_friends(twitter, screen_name):
    friendDetails = {}
    request = robust_request(twitter, 'friends/list', {'screen_name': screen_name, 'count': 100})
    friends = []
    for r in request:
        length = len(r['description'])
        if (length > 1):
            friends.append(r['screen_name'])
    friendsList = list(friends)
    for r in request:
        length = len(r['description'])
        if (length > 1):
            dict = {}
            dict['description'] = r['description']
            dict['name'] = r['name']
            dict['screen_name'] = r['screen_name']
            friendDetails[r['screen_name']] = dict
    return friendsList, friendDetails

def add_all_followers(twitter,users):
    followerDetails = {}
    for user in users:
        screen_name = user['screen_name']
        user['followers'], followerDet = get_followers(twitter, screen_name)
        followerDetails.update(followerDet)
    return followerDetails

def add_all_friends(twitter,users):
    friendsList = {}
    for user in users:
        screen_name = user['screen_name']
        user['friends'], friendDet = get_friends(twitter, screen_name)
        friendsList.update(friendDet)
    return friendsList


def get_census_names():
    males_url = 'http://www2.census.gov/topics/genealogy/' + \
                '1990surnames/dist.male.first'
    females_url = 'http://www2.census.gov/topics/genealogy/' + \
                  '1990surnames/dist.female.first'
    males = requests.get(males_url).text.split('\n')
    females = requests.get(females_url).text.split('\n')
    males_pct = get_percents(males)
    females_pct = get_percents(females)
    male_names = set([m.split()[0].lower() for m in males if m])
    female_names = set([f.split()[0].lower() for f in females if f])
    male_names = set([m for m in male_names if m not in female_names or
                      males_pct[m] > females_pct[m]])
    female_names = set([f for f in female_names if f not in male_names or
                        females_pct[f] > males_pct[f]])
    return male_names, female_names


def get_percents(name_list):
    return dict([(n.split()[0].lower(), float(n.split()[1]))
                 for n in name_list if n])


def main():
    twitter = get_twitter()

    screen_names =['Java','ThePSF','R_Programming','DatascienceCtrl','Java_EE']
    print('Collecting twitter data for : %s' % screen_names)
    users = sorted(get_users(twitter, screen_names), key=lambda x: x['screen_name'])
    print('found %d users with screen_names %s' %
          (len(users), str([u['screen_name'] for u in users])))
    print('finding followers...')
    followerDetails = add_all_followers(twitter,users)
    print('finding friends...')
    friendDetails = add_all_friends(twitter, users)
    pickle.dump(users, open('users.pkl', 'wb'))
    pickle.dump(friendDetails, open('friends.pkl', 'wb'))
    pickle.dump(followerDetails, open('followers.pkl', 'wb'))
    print('Getting census data for gender analysis..')
    male_names, female_names = get_census_names()
    pickle.dump(male_names, open('male_names.pkl', 'wb'))
    pickle.dump(female_names, open('female_names.pkl', 'wb'))
    print('Data collection completed')

if __name__ == '__main__':
    main()






