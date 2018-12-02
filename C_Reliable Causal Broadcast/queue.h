#include <stdio.h>
#include <stdlib.h>

typedef int boolean;
#define true 1
#define false 0

typedef struct Position {
    int row;
    int column;
} Position;

typedef struct History {
    int size;
    Position* data;
} History;

typedef struct Node {
    Position position;
    Position received[4];
    History history;
    boolean sent;
} Node;

int MAXRECEIVED = 4;
Node* queue;
int begin;
int end;


Node create_node(int row, int col);
void print_node(Node node);
void init_queue(int queueSize);
void add_node_queue(Node node);
Node take_node_queue();
void delete_node_queue();
int queue_is_empty();
int queue_size();


Node create_node(int row, int col) {
    Node node;
    int i;

    node.position.row = row;
    node.position.column = col;
    for(i = 0; i < MAXRECEIVED; i++) {
        node.received[i].row = -1;
        node.received[i].column = -1;
    }
    node.sent = 0;
    node.history.size = 0;

    return node;
}

void print_node(Node node) {
    int message = 0;
    printf("Node: position(%d, %d)", node.position.row, node.position.column);
    printf("\tmessages received: ");
    while(node.received[message].row != -1 && message < MAXRECEIVED) {
        printf(" (%d, %d) ", node.received[message].row, node.received[message].column);
        message++;
    }
    printf("\tHistory: { ");
    if(node.history.size > 0) {
        int h;
        for(h = 0; h < node.history.size; h++) {
            printf(" (%d, %d) ", node.history.data[h].row, node.history.data[h].column);
        }
    }
    printf(" }");

    printf("\tsent: %s\n", ((node.sent)? "true":"false"));
}

void init_queue(int queueSize) {
    queue = (Node *)malloc(sizeof(Node)*queueSize);
    begin = 0;
    end = 0;
}

void add_node_queue(Node node) {
    queue[end] = node;
    end++;
}

Node take_node_queue() {
    Node node = queue[begin];
    begin++;

    return node;
}

void delete_node_queue() {
    begin++;
}

boolean queue_is_empty() {
    return (begin == end)? true : false;
}

int queue_size() {
    return end-begin;
}

void print_queue() {
    int i = begin;
    for(i = begin; i < end; i++) {
        print_node(queue[i]);
    }
}
