#include "queue.h"

int NRow;
int NCol;
Node** graph;
int total_messages;

void print_graph_complete() {
    int row, col;
    printf("Total messages sent: %d",total_messages);
    for(row = 0; row < NRow; row++) {
        printf("\n");
        for(col = 0; col < NCol; col++) {
            Node node = graph[row][col];
            int message = 0;
            printf("|(%d, %d)\t", node.position.row, node.position.column);
            printf(" [ ");
            while(node.received[message].row != -1 && message < MAXRECEIVED) {
                printf("(%d, %d) ", node.received[message].row, node.received[message].column);
                message++;
            }
            printf("]\t{ ");
            if(node.history.size > 0) {
                int h;
                for(h = 0; h < node.history.size; h++) {
                    printf("(%d, %d) ", node.history.data[h].row, node.history.data[h].column);
                }
            }
            printf("}\t");
            printf("%s|\n", ((node.sent)? "true":"false"));
        }
    }
    printf("\n\n");
}

void print_graph_history() {
    int row, col;
    printf("Total messages sent: %d",total_messages);
    for(row = 0; row < NRow; row++) {
        printf("\n");
        for(col = 0; col < NCol; col++) {
            Node node = graph[row][col];
            printf("| { ");
            if(node.history.size > 0) {
                int h;
                for(h = 0; h < node.history.size; h++) {
                    printf("(%d, %d) ", node.history.data[h].row, node.history.data[h].column);
                }
            }
            printf("} |\t\t");
        }
        printf("\n");
    }
    printf("\n\n");
}

void print_graph_sent() {
    int row, col, i;
    printf("Total messages sent: %d",total_messages);
    printf("\n");
    for(row = 0; row < NRow; row++) {
        for(i = 0; i < NCol; i++) {
            printf(" --- ");
        }
        printf("\n");
        for(col = 0; col < NCol; col++) {
            Node node = graph[row][col];
            printf("| %d |", (node.sent));
        }
        printf("\n");
    }
    for(i = 0; i < NCol; i++) {
        printf(" --- ");
    }
    printf("\n\n");
}

void send_messages(Node node) {
    int nodeRow = node.position.row;
    int nodeCol = node.position.column;

    int up = nodeRow -1, down = nodeRow +1;
    int left = nodeCol-1, right = nodeCol +1;

    int parentRow = node.received[0].row, parentCol = node.received[0].column;

    //Send a message to all neighbors (if valid) (except the "parent")
    if(up >= 0) {
        if (!(parentRow == up && parentCol == nodeCol)) {
            //printf("|caso 1|\n");
            send_message(node.position, up, nodeCol);
        }
    }
    if(down < NRow) {
        if (!(parentRow == down && parentCol == nodeCol)) {
            //printf("|caso 2|\n");
            send_message(node.position, down, nodeCol);
        }
    }
    if(left >= 0) {
        if (!(parentRow == nodeRow && parentCol == left)) {
            //printf("|caso 3|\n");
            send_message(node.position, nodeRow, left);
        }
    }
    if(right < NCol) {
        if (!(parentRow == nodeRow && parentCol == right)) {
            //printf("|caso 4|\n");
            send_message(node.position, nodeRow, right);
        }
    }
}

void send_message(Position source, int destRow, int destCol) {
    int messageNumber = 0;

    //Find a valid position in destiny messages array
    while(graph[destRow][destCol].received[messageNumber].row != -1) {
        messageNumber++;
    }

    //Add a message(source position) in the destiny array
    graph[destRow][destCol].received[messageNumber] = source;

    //If it isn't in the send queue, put it and send the history vector
    if(!graph[destRow][destCol].sent) {
        add_node_queue(graph[destRow][destCol]);

        create_history(graph[source.row][source.column].history, graph[destRow][destCol].position);

        //Change the added Node sent flag.
        graph[destRow][destCol].sent = true;
    }

    total_messages++;
}

void create_history(History history, Position position) {

    int h;
    graph[position.row][position.column].history.size = history.size +1;
    graph[position.row][position.column].history.data = (Position*) malloc ((history.size +1) * sizeof(Position));

    for(h = 0; h < history.size; h++) {
        graph[position.row][position.column].history.data[h].row = history.data[h].row;
        graph[position.row][position.column].history.data[h].column = history.data[h].column;
    }
    graph[position.row][position.column].history.data[history.size] = position;
}

void init_starting_node(int row, int col) {
    graph[row][col].sent = true;
    graph[row][col].history.size = 1;
    graph[row][col].history.data = (Position*) malloc (sizeof(Position));
    graph[row][col].history.data[0].row = row;
    graph[row][col].history.data[0].column = col;
}

/* argv[1]: Square Matrix Size
 * argv[2]: Row position of starting node
 * argv[3]: Column position of starting node
 */
int main(int argc, char** argv) {
    int row, col;
    int iteration;
    total_messages = 0;

    //Graph Representation
    NRow = atoi(argv[1]);
    NCol = atoi(argv[2]);
    graph = (Node **) malloc(NRow * sizeof(Node *));
    for (row = 0; row < NRow; row++)
        graph[row] = (Node *)malloc(NCol * sizeof(Node));


    //Graph Initialization
    for(row = 0; row < NRow; row++)
        for(col = 0; col < NCol; col++)
            graph[row][col] = create_node(row, col);


    //Starting Node (program argument)
    int startNodeRow = atoi(argv[3]), startNodeCol = atoi(argv[4]);
    init_starting_node(startNodeRow, startNodeCol);

    //Queue Initialization
    init_queue(NRow*NCol);
    add_node_queue(graph[startNodeRow][startNodeCol]);

    //Send Messages
    printf("Starting Matrix\n");
    print_graph_sent(graph);


    iteration = 1;
    while(!queue_is_empty()) {
        Node node = take_node_queue();
        send_messages(node);
        printf("Iteration: %d -- ", iteration);
        iteration++;
        printf("Working Node: (%d,%d)\n", node.position.row, node.position.column);
        print_graph_sent(graph);
    }

    printf("Final Matrix\n");
    print_graph_sent(graph);

    return 0;
}