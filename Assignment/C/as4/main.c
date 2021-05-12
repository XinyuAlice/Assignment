#include <stdio.h>
#include <stdlib.h>
#include <string.h>
//use struct to store pagenum and frame num
typedef struct {
    int pagenum;
    int frame;

} parameters;
//use search function to find the corresponding frame number
int search(parameters data1[4],int page)
{
    int j=0;
    while(data1[j].pagenum!=page){
        j++;
    }
    return data1[j].frame;
}
int main(int argc, const char * argv[]) {
    FILE *ifp1, *ifp2, *ofp;
    //logical address
    int logicaladdr[64];
    //physical address
    int physicaladdr[64];
    ifp1 = fopen("LogicalAddress.txt", "r");
    ifp2 = fopen("PageTable.txt", "r");
    int count = 0, i = 0;
    char string1[350];
    char string2[350];
    char *tok;
    int quotient,offset;
    parameters data1[4];
    //parameters *data1 = (parameters *) malloc(sizeof(parameters));
    //fail to open logical address
    if (ifp1 == NULL) {
        fprintf(stderr, "Can't open input file LogicalAddress.txt!\n");
        exit(-1);
    }
    //read logical address.txt
    while (fgets(string1, sizeof(string1), ifp1) != NULL) {
        printf("%s", string1);
        int num = atoi(string1);
        logicaladdr[count] = num;
        count++;
    }
    count--;//line of logical address

    for (i = 0; i < count; i++) {
        printf("%d\n", logicaladdr[i]);
    }
    fclose(ifp1);
    if (ifp2 == NULL) {
        fprintf(stderr, "Can't open input file PageTable.txt!\n");
        exit(-1);
    }
    //read pagenum.txt
      for(i=0;i<4;i++){
   // while(fgets(string2, sizeof(string2),ifp2)!='\0') {
        fgets(string2, sizeof(string2),ifp2);
        printf("%s", string2);
        tok = strtok(string2, ",");
        printf("%s\n",tok);
        data1[i].pagenum=atoi(tok);
        tok=strtok(NULL,",");
        printf("%s\n",tok);
        data1[i].frame=atoi(tok);

    }
      fclose(ifp2);
      int j;
 /* int j;
    for (j = 0; j < i; j++) {
        printf("page number: %d\n", data1[j].pagenum);
        printf("frame number: %d\n", data1[j].frame);
    }*/
    ofp = fopen("PhysicalAddress.txt", "w");

    if (ofp == NULL) {
        printf("File cannot open!");
        exit(-1);
    }
    for (j = 0; j < count; j++) {
        quotient = logicaladdr[j] / 4;
        offset=logicaladdr[j]%4;
        physicaladdr[j]=search(data1,quotient)*4+offset;
        printf("%d\n",physicaladdr[j]);
        fprintf(ofp,"%d\n",physicaladdr[j]);
    }
    //close the output file
    fclose(ofp);
  return 0;
}


