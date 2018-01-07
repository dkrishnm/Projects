"""
cluster.py
"""

import matplotlib.pyplot as plt
import networkx as nx
import pickle

# partition using GIRVAN-NEWMAN Algorithm
def partition_girvan_newman(graph):
    graphCopy = graph.copy()
    betweenness = nx.edge_betweenness_centrality(graphCopy)
    sortedBetweeness = sorted(betweenness.items(), key=lambda x: (-x[1], x[0]))
    for edge in sortedBetweeness:
        toDel = isDelete(edge[0][0], edge[0][1], graphCopy)
        if (toDel):
            graphCopy.remove_edge(edge[0][0], edge[0][1])
        if (nx.number_connected_components(graphCopy) > 5):
            break
    components = list(nx.connected_component_subgraphs(graphCopy))
    #print("Number of components :" % len(components))
    return graphCopy, components

def isDelete(node1, node2, graphCopy):
    nodeEdgeDict = getNodeEdges(graphCopy)
    edges1 = nodeEdgeDict[node1]
    edges2= nodeEdgeDict[node2]
    if (len(edges1) > 1 and len(edges2) > 1):
        return True
    else:
        return False

def getNodeEdges(graphCopy):
    nodeEdgeDict = {}
    for node in graphCopy.nodes():
        if (node not in nodeEdgeDict.keys()):
            edges = []
            edges.extend(graphCopy.edges([node]))
            nodeEdgeDict[node] = edges
        else:
            edges = nodeEdgeDict[node]
            edges.extend(graphCopy.edges([node]))
            nodeEdgeDict[node] = edges
    return nodeEdgeDict


def draw_network(graph, users, filename):
    plt.figure(figsize=(16, 16))
    labels = {}
    for user in users:
        labels[user['screen_name']] = user['screen_name']
    pos = nx.spring_layout(graph)
    #nx.draw_networkx(graph, pos, with_labels=True, labels=labels, alpha=.5, width=.6,node_size=100)
    nx.draw_networkx(graph, nx.spring_layout(graph), labels=labels, with_labels=True, node_size=100,
                     node_color='#f08080', edge_color='#c5c5c5', font_size=9, font_weight='bold')
    plt.savefig(filename)
    plt.show()

def create_graph(users):
    graph = nx.Graph()
    for user in users:
        graph.add_node(user['screen_name'])
        followers = user['followers']
        friends = user['friends']
        for follower in followers:
            graph.add_edge(follower, user['screen_name'])
        for friend in friends:
            graph.add_edge(friend, user['screen_name'])

    return graph

def main():
    users = pickle.load(open('users.pkl', 'rb'))
    graph = create_graph(users)
    draw_network(graph, users, 'BeforeClustering.png')
    graphCopy, clusters = partition_girvan_newman(graph)
    draw_network(graphCopy, users, 'AfterClustering.png')
    pickle.dump(graphCopy, open('graph.pkl', 'wb'))

if __name__ == '__main__':
    main()

