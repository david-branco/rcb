#include "queue.h"

int main(int argc, char **argv){

    Node node1 = create_node(0, 0);
    Node node2 = create_node(1, 2);
    node2.received[0].row = 3; node2.received[0].column = 4;
    node2.history.size = 2;
    node2.history.data = (Position*) malloc (node2.history.size*sizeof(Position));
    node2.history.data[0] = node1.position;
    node2.history.data[1] = node2.position;
    node2.sent = true;
    Node node3 = create_node(3, 4);
    node3.received[0].row = 1; node3.received[0].column = 1;
    node3.received[1].row = 2; node3.received[1].column = 2;
    print_node(node1);
    print_node(node2);
    print_node(node3);

    printf("\n\n");
    init_queue(10);
    add_node_queue(node1);
    add_node_queue(node2);
    add_node_queue(node3);
    print_queue();
    printf("\n");
    Node aux = take_node_queue();
    print_node(aux);
    printf("\n");
    print_queue();




    printf("\nFIM\n");

}