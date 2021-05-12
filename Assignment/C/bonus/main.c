#include <stdio.h>
#include <stdlib.h>
//learn the Page replacement from course content and resources online for the implementation
//use the printPage function to print result to file which will be more clear
void printPage(FILE* file,int frame[5],int framenum);

int main(int argc, const char * argv[]) {
    //initialize input file and output file
    FILE *ifp,  *ofp;
    int framenum=0;
    int referString[20];
    int pagenum=0;//count how many page in reference string: 1 to 20 pages
    int j=0;
    int avail=0;
    int k=0;
    ifp = fopen("Input.txt", "r");
    if (ifp == NULL) {
        printf( "Can't open input file input.txt!\n");
        exit(-1);
    }
    //read frame
    //fscanf to read first line: framenum
    fscanf(ifp,"%d",&framenum);
    fgetc(ifp);
    //frame is 1-5
    if (framenum < 1||framenum>5) {
        printf("Error! The number of frames in physical memory has to be from 1 to 5!\n");
        return -1;
    }
    //read reference string
    //fscanf to read the rest of the reference strings
    while (fscanf(ifp, "%d%*[^,\n]", &referString[pagenum++]) == 1)
        fgetc(ifp);
    pagenum--;//num in the refernce string
    fclose(ifp);
    //reference string is 1 to 20
    if (pagenum < 1||pagenum>20) {
        printf("The reference string has to be 1 to 20 !\n");
        return -1;
    }
    printf("frame num: %d\n",framenum);
    //check if read file process is right
    for(k=0;k<pagenum;k++) {
        printf("reference string: %d\n", referString[k]);
    }
    ofp = fopen("Output.txt", "w");

    if (ofp == NULL) {
        printf("Output file cannot open");
        return -1;
    }
    int i=0;
    //page fault
    int pagefault=0;
    //frame range is 1 to 5
    int frame[5];
    //initialize for free frames with -1
    //the frame is initially empty
    for(i=0;i<framenum;i++) {
        frame[i] = -1;
    }
    printf("Page replacement process: \n");
    for(i=0;i<pagenum;i++)
    {
        avail=0;
        //if there is available frame, frame=1
       for(k=0;k<framenum;k++) {
            if (frame[k] == referString[i]) {
                avail = 1;
            }
        }
            //if no available frame, replace oldest and update index and pagefault imcrement
            if (avail==0) {
                frame[j] = referString[i];//replace the oldest
                j = (j + 1) % framenum;//update the j which is the index in the frame
                pagefault++;

            }
            //print page process
            printPage(ofp,frame,framenum);
    }


    fprintf(ofp,"\n");
    fprintf(ofp, "\nNumber of Page Faults : %d", pagefault);
    printf("\nNumber of page faults: %d",pagefault);
    fclose(ofp);
    return 0;

}
//print the page replacement process
void printPage(FILE *file, int frame[5], int n) {
    int i;
    for (i = 0; i < n - 1; i++) {
        if (frame[i] == -1) {
            fprintf(file, "N\t");
            printf( "N\t");
        } else {
            fprintf(file, "%d\t", frame[i]);
            printf( "%d\t",frame[i]);
        }
    }
    if (frame[i] == -1) {
        fprintf(file, "N\n");
        printf( "N\n");
    } else {
        fprintf(file, "%d\n", frame[i]);
        printf( "%d\n",frame[i]);
    }
}
