#include <stdio.h>
#include <conio.h>
#include <string.h>

struct contactStruct{
    char phone[10];
    char name[20];
    char email[30];
};

struct contactStruct contactList[10];
int contactSize=0;

void addContact(char name[],char phone[],char email[]){
    if (name==NULL || phone==NULL)
    {
        printf("Name and Phone Number should be filled\n");
        return;
    }
    
    strcpy(contactList[contactSize].name,name);
    strcpy(contactList[contactSize].phone,phone);
    strcpy(contactList[contactSize].email,email);
    contactSize++;
}

int searchContact(char name[]){
    if (name==NULL)
    {
        printf("Please provide a name to Search!\n");
        return 0;
    }
    
    for (int i = 0; i < contactSize; i++){
        if (contactList[i].name==name)
        {
            printf("Name :\t\t%s\nContact :\t%s\nEmail :\t\t%s\n",contactList[i].name,contactList[i].phone,contactList[i].email);
            return 0;
        }
    }
    printf("Contact Not foudn!\n");
    return 0;
}

void displayContact(struct contactStruct list[]){
    if (contactSize<=0){
        printf("Currently There are no Contacts!\n");
        return;
    }
    
    for (int i = 0; i < contactSize; i++){
        printf("Name :\t\t%s\nContact :\t%s\nEmail :\t\t%s\n",list[i].name,list[i].phone,list[i].email);
    }
        
}

void contactMenu(){
    printf("Enter keywords as below to call the functions:\n1 - Menu \t:\tFor menu of keywords\n2 - Add \t:\tTo add new contact\n3 - Search \t:\tTo search a contact\n4 - Display \t:\tTo show all Contacts\n0 - Exit \t:\tTo close the manager\n");
}

int main(){
    int Error=5;
    int inputs=1;
    contactMenu();
    while (inputs!=0)
    {
        scanf("%d",&inputs);
        if (inputs==1)
        {
            contactMenu();
        }
        else if (inputs==2)
        {
            printf("Please Enter the Name, Number and email in this sequence:\n");
            char name[20],phone[10],email[30];
            scanf("%20s %10s %30s",&name,&phone,&email);
            addContact(name,phone,email);
            printf("Added Succesfully! Enter 1 for Menu and 4 to display the contacts.\n");
        }
        else if(inputs==3)
        {
            printf("Enter a name to Search :");
            char name[20];
            searchContact(name);
        }
        else if (inputs==4)
        {
            displayContact(contactList);
        }
        else if (inputs==0)
        {
            return 0;
        }
        else if (Error<=0)
        {
            return 0;
        }
        
        else{
            printf("Unknown choice Close manager after %d chances", Error);
            Error--;
        }
        
        
        
    }
    

    return 0;
}
