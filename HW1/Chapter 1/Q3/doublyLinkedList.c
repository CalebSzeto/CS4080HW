#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct Node {
    char* data;
    struct Node* next;
    struct Node* prev;
} Node;

Node* createNode(const char* str) {
    Node* node = malloc(sizeof(Node));
    if (!node) return NULL;
    
    node->data = malloc(strlen(str) + 1);
    if (!node->data) {
        free(node);
        return NULL;
    }
    
    strcpy(node->data, str);
    node->next = NULL;
    node->prev = NULL;
    return node;
}

Node* insert(Node** head, const char* str) {
    Node* newNode = createNode(str);
    if (!newNode) return *head;
    
    if (*head) {
        (*head)->prev = newNode;
        newNode->next = *head;
    }
    *head = newNode;
    return *head;
}

Node* find(Node* head, const char* str) {
    Node* current = head;
    while (current) {
        if (strcmp(current->data, str) == 0) {
            return current;
        }
        current = current->next;
    }
    return NULL;
}

Node* delete(Node** head, const char* str) {
    Node* node = find(*head, str);
    if (!node) return *head;
    
    if (node->prev) {
        node->prev->next = node->next;
    } else {
        *head = node->next;  
    }
    
    if (node->next) {
        node->next->prev = node->prev;
    }
    free(node->data);
    free(node);
    return *head;
}


void printList(Node* head) {
    Node* current = head;
    while (current) {
        printf("%s", current->data);
        if (current->next) printf(" <-> ");
        current = current->next;
    }
    printf("\n");
}

int main() {
    Node* head = NULL;
    
    printf("Doubly Linked List\n");
    printf("1. Insert: one, two, three\n");
    head = insert(&head, "one");
    head = insert(&head, "two");
    head = insert(&head, "three");
    printList(head);
    
    printf("\n2. Find 'two': ");
    Node* found = find(head, "two");
    printf("%s\n", found ? "Found" : "Not found");
    
    printf("\n3. Delete 'two'\n");
    head = delete(&head, "two");
    printList(head);
    
    printf("\n4. Delete 'four' but it doesn't exist\n");
    head = delete(&head, "four");
    printList(head);

    return 0;
}