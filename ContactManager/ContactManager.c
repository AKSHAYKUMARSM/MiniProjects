#include <stdio.h>
#include <conio.h>
#include <string.h>

struct contactStruct{
    char name[20];
    char phone[20];    
    char email[30];
};

struct contactStruct contactList[10];

void addContact(char name[],char phone[],char email[]){

}

void displayContact(struct contactStruct list[]){
    int length=sizeof(&list)/sizeof(list[0]);
    printf("%d",length);
    
    // printf("Name :\t\t%s\nContact :\t%s\nEmail :\t\t%s\n",list.name,list.phone,list.email);
}

int main(){

    strcpy(contactList[0].name,"Akshay Kumar");
    strcpy(contactList[0].phone,"9656435214");
    strcpy(contactList[0].email,"akshay1234@gmail.com");
    displayContact(contactList);

    return 0;
}
