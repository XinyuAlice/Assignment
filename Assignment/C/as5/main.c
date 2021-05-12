#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int unsortarr[500];//the global array shared by the threads
int sortedarr[500];//the global array to store the merged array by the thread
/*structure for passing data to threads */
typedef struct {
    int starting_index;
    int ending_index;
} parameters;

//insertion sort
void *sorter(void *params);//thread that performs insertion sorting algorithm
void *merger(void *params);//thread that performs merging of results

int main(int argc, const char * argv[]) {
    FILE *ifp, *ofp;
    int i=0;
    ifp = fopen("IntegerList.txt", "r");
    if (ifp == NULL) {
        fprintf(stderr, "Can't open input file IntegerList.txt!\n");
        exit(-1);
    }
    //use fscanf to read the file
    while(fscanf(ifp,"%d%*[^,\n]",&unsortarr[i++])==1)
        fgetc(ifp);
    fclose(ifp);
    //i is the length of the array; which has to remove the last one since last one does not comma behind
    i--;
    //use four numbers to represent start index and end index of two subarray
    int start1,start2,end1,end2;
    start1=0;
    end1=i/2-1;
    start2=i/2;
    end2=i-1;

    pthread_t threads[3];
    //the thread identifier
    pthread_attr_t attr;//set of thread attributes
    //set the default attributes of the thread
    pthread_attr_init(&attr);

    /* create first sorting threads */
    parameters *data1 = (parameters *) malloc(sizeof(parameters));
    data1->starting_index= start1;
    data1->ending_index = end1;
    pthread_create(&threads[0], &attr, sorter, data1);

    /*create second sorting thread*/
    parameters *data2 = (parameters *) malloc(sizeof(parameters));
    data2->starting_index= start2;
    data2->ending_index = end2;
    pthread_create(&threads[1], &attr, sorter, data2);

    //wait for sorting threads to terminate
    pthread_join(threads[0],NULL);
    pthread_join(threads[1],NULL);


    /*establish the merge thread*/
    parameters *data3 = (parameters *) malloc(sizeof(parameters));
    data3->starting_index= start2;
    data3->ending_index = end2;
    pthread_create(&threads[2], &attr, merger, data3);

    //wait for the merge thread
    pthread_join(threads[2],NULL);

    ofp = fopen("SortedIntegerList.txt", "w");

    if (ofp == NULL) {
        printf("File cannot open!");
        exit(-1);
    }
    int j;

    for(j=0;j<i;j++){
        // printf("%d\n",sortedarr[j]);
        fprintf(ofp,"%d",sortedarr[j]);//use fprintf to write to the file
        if(j<i-1)
            fputc(',',ofp);//add comma to the file
    }
    fclose(ofp);

    return 0;

}
void *sorter(void *params){
    //sort algorithm implementation
    /* a[start] to a[end] is the array to sort */
    parameters *parameters1 = (parameters *) params;
    int start,end;
    start=parameters1->starting_index;
    end=parameters1->ending_index;
    //learn the insertion sort algorithm from geeksforgeeks; it is pick one element arr[i] and insert into sorted sequence
    int i,j,key;
    for(i=start+1;i<=end;i++)
    {
        key = unsortarr[i];

        j = i-1;
        //if greater than the key, then move the value to the front position
        while (j >= start &&  unsortarr[j]>key)
        {
            unsortarr[j+1] = unsortarr[j];
            j--;
        }
        //insert the element into [j+1]
        unsortarr[j+1] = key;
    }
    pthread_exit(0);
}


void *merger(void *params){
    //merging algorithm implementation
    int start,end;
    parameters *parameters2 = (parameters *) (params);
    start=parameters2->starting_index;
    end=parameters2->ending_index;

    int i = 0, j =start, k = 0;

    // Traverse both array
    while (i< start&& j <=end)
    {
        // if current element of first array is smaller than current element of second array. If yes then store first; otherwise, store second

        if (unsortarr[i] < unsortarr[j])
            sortedarr[k++] = unsortarr[i++];
        else
            sortedarr[k++] = unsortarr[j++];
    }

    // Store remaining elements of first array
    while (i <start)
        sortedarr[k++] = unsortarr[i++];

    // Store remaining elements of second array
    while (j <=end)
        sortedarr[k++] = unsortarr[j++];
    pthread_exit(0);
}
