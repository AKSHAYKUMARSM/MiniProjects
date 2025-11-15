#include <stdio.h>
#include <conio.h>
#include <string.h>

typedef struct{
    char phone[10];
    char name[20];
    char email[30];
} contactStruct;

contactStruct contactList[10];
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
    int index=-1;
    if (name==NULL)
    {
        printf("Please provide a name to Search!\n");
        return -1;
    }
    
    for (int i = 0; i < contactSize; i++){
        if (strcmp(contactList[i].name,name)==0)
        {
            index=i;
            break;
        }
    }
    if (index>=0)
    {
        printf("Contact found!\n");
        return index;
    }
    else{
    printf("No match!\n");
    return -1;
    }
}

void displayContact(int index){    
    if (contactSize<=0){
        printf("Currently There are no Contacts!\n");
        return;
    }

    if (index<-1)
    {
        for (int i = 0; i < contactSize; i++){
            printf("\nName\t:\t%s\nContact\t:\t%s\nEmail\t:\t%s\n\n",contactList[i].name,contactList[i].phone,contactList[i].email);
        }
    }
    else if (index==-1)
    {
        printf("No Contacts!\n");
    }
    
    else{
        printf("\nName\t:\t%s\nContact\t:\t%s\nEmail\t:\t%s\n\n",contactList[index].name,contactList[index].phone,contactList[index].email);
    }
        
}

void updateContact(int index){
    if (index<0 || index>contactSize)
    {
        printf("Index out of bound!\n");
        return;
    }
    displayContact(index);
    printf("Enter details same as above and only change that u want to correct :\nName : ");
    char name[20],phone[10],email[30];
    scanf("%s",name);
    printf("Number : ");
    scanf("%s",phone);
    printf("Email : ");
    scanf("%s",email);
    strcpy(contactList[index].name,name);
    strcpy(contactList[index].phone,phone);
    strcpy(contactList[index].email,email);
    printf("Update Successfull!\n");
}

void deleteContact(int index){
    if (index<0 || index>contactSize)
    {
        printf("Index out of bound!\n");
        return;
    }
    displayContact(index);
    int del=0;
    printf("Delete the contact\n[1] for yes, [0] for no");
    scanf("%d",&del);

    if (del==1){
    
    for (int i = index; i <contactSize-1; i++)
    {
        contactList[i]=contactList[i+1];
    }
    contactSize--;
    printf("Deleted Succesfully!\n");
    }
    else{
        printf("Deletion Cancelled!\n");
    }
    
}

void contactMenu(){
    printf("Enter keywords as below to call the functions:\n1 - Menu \t:\tFor menu of keywords\n2 - Add \t:\tTo add new contact\n3 - Search \t:\tTo search a contact\n4 - Update \t:\tTo update a existing contact\n5 - Delete \t:\tTo Delete a existing contact\n6 - Display \t:\tTo show all Contacts\n0 - Exit \t:\tTo close the manager\n");
}

int main(){
    int Error=5;
    int inputs=1;
    contactMenu();
    while (inputs!=0)
    {
        scanf("%d",&inputs);
        switch (inputs)
        {
        case 0:return 0;break;

        case 1://Menu
            contactMenu();
            Error++;
            break;

        case 2:{//Add to contact
            printf("Please Enter the Details :\nName : ");
            char name[20],phone[10],email[30];
            scanf("%s",name);
            printf("\nPhone Number : ");
            scanf("%s",phone);
            printf("\nEmail Address : ");
            scanf("%s",email);
            addContact(name,phone,email);
            printf("Added Succesfully! Enter 1 for Menu and 6 to display the contacts.\n");
            Error++;
            break;
        }

        case 3:{//Search a contact
            printf("Enter a name to Search :\n");
            char name[20];
            scanf("%s",name);
            displayContact(searchContact(name));
            Error++;
            break;
        }

        case 4:{//Update a contact
            printf("Enter a name to Search and Update :\n");
            char name[20];
            scanf("%s",name);
            updateContact(searchContact(name));
            Error++;
            break;
        }

        case 5:{//Delete a contact
            printf("Enter a name to Delete :\n");
            char name[20];
            scanf("%s",name);
            deleteContact(searchContact(name));
            Error++;
            break;
        }

        case 6:{//Display the contacts
            displayContact(-2);
            Error++;
            break;
        }
        
        default://Wrong Choice
            if (Error<=0)
            {
                return 0;
            }
            
            printf("Wrong choice Close manager after %d chances\n", Error);
            Error--;
            break;
        }
        
    }
    

    return 0;
}
