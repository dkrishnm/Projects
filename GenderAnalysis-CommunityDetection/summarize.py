"""
sumarize.py
"""
import pickle
import networkx as nx

def find_stats(users,followers,graph):
    followersCount = 0
    friendsCount = 0
    count = 0
    maleCount = 0
    femaleCount = 0
    for user in users:
        followersCount += len(user['followers'])
        friendsCount += len(user['friends'])
    num_users = len(users) + followersCount + friendsCount
    components = list(nx.connected_component_subgraphs(graph))
    maleUser = ''
    femaleUser = ''
    num_nodes = 0
    for component in components:
        num_nodes+= nx.number_of_nodes(component)
    avg_nodes = num_nodes / len(components)
    for user in list(followers.keys()):
        followerData = followers[user]
        if('gender' in followerData.keys()):
            if (followerData['gender'] == 'male'):
                maleCount +=1
                if(len(maleUser) == 0):
                    maleUser = followerData
            elif(followerData['gender'] == 'female'):
                femaleCount +=1
                if(len(femaleUser) == 0):
                    femaleUser = followerData
        else:
            count +=1

    summary = 'Number of users collected: ' + str(num_users) + '\n' + \
        'Number of communities discovered: ' + str(len(components)) + '\n' + \
        'Average number of users per community: ' + str(avg_nodes)+'\n' + \
        'Number of instances per class found: \n\t ' +\
        'Male Count : ' + str(maleCount) + '\n\t '+\
        'Female Count : ' + str(femaleCount) + '\n' + \
        'Female example: ' + '\n' + \
        '\t Name :' + femaleUser['name'] + '\n' + \
        '\t Screen Name :' + femaleUser['screen_name'] + '\n' + \
        '\t Gender :' + femaleUser['gender'] + '\n' + \
        '\t Description :' + femaleUser['description'] + '\n'+\
        'Male example : ' + '\n'+ \
         '\t Name :' +  maleUser['name']+ '\n'+ \
         '\t Screen Name :'+ maleUser['screen_name']+ '\n'+ \
         '\t Gender :' + maleUser['gender'] + '\n'+\
         '\t Description :'+ maleUser['description']+ '\n'
    print (summary)
    f = open('summary.txt', 'w')
    f.write(summary)
    f.close()

def main():
    users = pickle.load(open('users.pkl', 'rb'))
    graph = pickle.load(open('graph.pkl', 'rb'))
    folllowers = pickle.load(open('followerDetails.pkl', 'rb'))
    find_stats(users,folllowers,graph)

if __name__ == '__main__':
    main()
