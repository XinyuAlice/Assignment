#include<stdlib.h>
#include<stdio.h>
#include<string.h>
#define MAXROW 50

struct ComputationTask{
    char *taskName[32];
    int arrivalTime;
    int burstTime;
};

struct OrderedTask{
    char *taskName[32];
    int startTime;
    int endTime;
    int waitTime;
};

int splitString(char *argv[],char *string);
void FCFS(struct OrderedTask orderedList[],struct ComputationTask taskList[],int taskNum,struct ComputationTask taskList_BOSS[],FILE *wf);
void printTable(struct OrderedTask taskList[],int taskNum,FILE *wf);
//TODO
void RR(struct OrderedTask orderedList[],struct ComputationTask taskList[],int taskNum,struct ComputationTask taskList_BOSS[],FILE *wf);
void NSJF(struct OrderedTask orderedList[],struct ComputationTask taskList[],int taskNum,struct ComputationTask taskList_BOSS[],FILE *wf);
//TODO
void PSJF(struct OrderedTask orderedList[],struct ComputationTask taskList[],int taskNum,struct ComputationTask taskList_BOSS[],FILE *wf);
void sortByBurst(struct ComputationTask taskList[],int taskNum,int currTime,int finishedTime);

//int totalWaitingTimeRR(struct OrderedTask orderedList[],struct ComputationTask taskList[],int oNum,int tNum);
//int totalWaitingTimePSJF(struct OrderedTask orderedList[],struct ComputationTask taskList[],int oNum,int tNum);
float AverageWaitTime(struct OrderedTask orderedList[],struct ComputationTask taskList[],int oNum,int tNum);

char line[64];
char task[MAXROW][64];

int main(int argc, const char *argv[]){
    //read from the text file line by line and put into a string array
    FILE *fp = fopen("TaskSpec.txt","r");
    if(fp==NULL){
        printf("file open error\n");
        return -1;
    }
    int taskNum=0;
    while(fgets(task[taskNum],64,fp)){
        task[taskNum][strlen(task[taskNum])-1] = '\0';
        taskNum++;
    }
    fclose(fp);

    char *tempResult[32];

    //initialize the taskList
    //struct ComputationTask *taskList = malloc(taskNum*sizeof(int));
    struct ComputationTask *taskList_BOSS = malloc(taskNum * sizeof(struct ComputationTask));
    struct ComputationTask *taskList_FCFS = malloc(taskNum * sizeof(struct ComputationTask));
    struct ComputationTask *taskList_NSJF = malloc(taskNum * sizeof(struct ComputationTask));
    struct ComputationTask *taskList_PSJF = malloc(taskNum * sizeof(struct ComputationTask));
    struct ComputationTask *taskList_RR = malloc(taskNum * sizeof(struct ComputationTask));

    struct ComputationTask currentTask;
    int j;
    for(j=0;j<taskNum;j++){
        splitString(tempResult,task[j]);
        strcpy(currentTask.taskName,tempResult[0]);
        currentTask.arrivalTime=atoi(tempResult[1]);
        currentTask.burstTime=atoi(tempResult[2]);
        taskList_BOSS[j]=currentTask;
        taskList_FCFS[j]=currentTask;
        taskList_NSJF[j]=currentTask;
        taskList_PSJF[j]=currentTask;
        taskList_RR[j]=currentTask;
    }
    /***** till now, all the data are stored into our struct array******/


    struct OrderedTask *orderedList = malloc(taskNum*sizeof(struct OrderedTask));
    struct OrderedTask *orderedList_FCFS = malloc(taskNum*sizeof(struct OrderedTask));
    struct OrderedTask *orderedList_NSJF = malloc(taskNum*sizeof(struct OrderedTask));
    struct OrderedTask *orderedList_PSJF = malloc(taskNum*sizeof(struct OrderedTask));
    struct OrderedTask *orderedList_RR = malloc(taskNum*sizeof(struct OrderedTask));
    //orderedList = realloc(orderedList,(taskNum+1)*64);

    FILE *wf = fopen("output.txt","w");
    wf=fopen("output.txt","a");
    fprintf(wf,"FCFS:\n");
    FCFS(orderedList_FCFS,taskList_FCFS,taskNum,taskList_BOSS,wf);

    fprintf(wf,"\nNSJF:\n");
    NSJF(orderedList_NSJF,taskList_NSJF,taskNum,taskList_BOSS,wf);

    fprintf(wf,"\nPSJF:\n");
    PSJF(orderedList_PSJF,taskList_PSJF,taskNum,taskList_BOSS,wf);

    fprintf(wf,"\nRR:\n");
    RR(orderedList_RR,taskList_RR,taskNum,taskList_BOSS,wf);
    /****output section******/
    fclose(wf);
    return 0;
}

//This function is copied from my first assignment
int splitString(char *argv[],char *string){
    int i=0;
    char delim[]=",";
    argv[0]=strtok(string,delim);
    while(argv[i]!=NULL){
        argv[++i] = strtok(NULL,delim);
    }
    return 0;
}

void FCFS(struct OrderedTask orderedList[],struct ComputationTask taskList[],int taskNum,struct ComputationTask taskList_BOSS[],FILE *wf){
    int i;
    int currTime=taskList[0].arrivalTime;
    //int waitTimeSum=0;
    //float averageWaitTime=0;
    for(i=0;i<taskNum;i++){
        strcpy(orderedList[i].taskName,taskList[i].taskName);
        if(currTime>taskList[i].arrivalTime){
            orderedList[i].startTime=currTime;
        }else {
            currTime=taskList[i].arrivalTime;
            orderedList[i].startTime=currTime;
        }
        orderedList[i].waitTime=orderedList[i].startTime-taskList[i].arrivalTime;
        orderedList[i].endTime = currTime+taskList[i].burstTime;
        currTime+=taskList[i].burstTime;
        // waitTimeSum+=orderedList[i].waitTime;
    }
    //averageWaitTime=(float)waitTimeSum/(float)taskNum;
    float averageWaitTime=AverageWaitTime(orderedList,taskList_BOSS,taskNum,taskNum);
    //write into file
    printTable(orderedList,taskNum,wf);
    fprintf(wf,"Average Wait time is %.2f\n",averageWaitTime);
    //printf("task num is %d\n",taskNum);
    //printf("FCFS:\n");
}

void printTable(struct OrderedTask taskList[],int taskNum,FILE *wf){
    int i;
    for (i=0;i<taskNum;i++) {
        fprintf(wf,"%s\t%d\t%d\n", taskList[i].taskName, taskList[i].startTime, taskList[i].endTime);
    }

}

void RR(struct OrderedTask orderedList[],struct ComputationTask taskList[],int taskNum,struct ComputationTask taskList_BOSS[],FILE *wf){
    int currTime = taskList[0].arrivalTime;
    int finishedNum=0;
    int waitTimeSum=0;
    int currTask=0;
    int roundtime=4;
    struct ComputationTask currentTask = taskList[0];
    struct ComputationTask pTask;
    pTask=taskList[1];
    //keep track of how many steps we process to finish.
    int totalRun=0;
    //put the first task into our ordered list.
    strcpy(orderedList[totalRun].taskName,currentTask.taskName);
    while(finishedNum<taskNum){
        //change to 4 something
        //printf("Total run:%d\n",totalRun);
        if(currentTask.burstTime<=roundtime&& currentTask.burstTime>0){
            roundtime=currentTask.burstTime;
        }
        currTime+=roundtime;
        currentTask.burstTime-=roundtime;
        taskList[currTask].burstTime-=roundtime;

        //printf("%s\n",currentTask.taskName);
        if(currentTask.burstTime==0){
            finishedNum++;
        }

//REALLY SHITTY CODE HERE, WORK ON LATER!!!!!!!!!!!!!
        int foundLeft=0;
        currTask++;
        if(currTask>=taskNum){
            currTask-=taskNum;
        }
        int i;
        for(i=0;i<taskNum;i++){
            if(foundLeft==0){
                if(taskList[currTask].burstTime==0){
                    currTask++;
                    if(currTask>=taskNum){
                        currTask-=taskNum;
                    }
                }else{
                    foundLeft=1;
                }
            }
        }
        //switch to next task
        roundtime=4;
//SHITTY END HERE.

        pTask=taskList[currTask];

        if(totalRun<taskNum){
            //printf("1\n");
            //if current task changed, save current task into ordered list.
            strcpy(orderedList[totalRun].taskName,currentTask.taskName);
            orderedList[totalRun].endTime=currTime;
            orderedList[totalRun].startTime=currentTask.arrivalTime;
            totalRun++;
            //change task
            currentTask=pTask;
            currentTask.arrivalTime=currTime;

        }else{
            //printf("2\n");
            orderedList = realloc(orderedList,(taskNum+1)*sizeof(struct OrderedTask));
            strcpy(orderedList[totalRun].taskName,currentTask.taskName);
            orderedList[totalRun].endTime=currTime;
            orderedList[totalRun].startTime=currentTask.arrivalTime;
            //finishedNum++;
            totalRun++;
            //change task
            currentTask=pTask;
            currentTask.arrivalTime=currTime;
        }
        //printf("Name %s, startTime %d\n",currentTask.taskName,currentTask.arrivalTime);
    }

    //loat averageWaitTime=(float)(totalWaitingTimeRR(orderedList,taskList_BOSS,totalRun,taskNum))/(float)taskNum;
    float averageWaitTime=AverageWaitTime(orderedList,taskList_BOSS,totalRun,taskNum);
    //printf("RR:\n");
    printTable(orderedList,totalRun,wf);
    fprintf(wf,"Average Wait time is %.2f\n",averageWaitTime);
}

void NSJF(struct OrderedTask orderedList[],struct ComputationTask taskList[],int taskNum,struct ComputationTask taskList_BOSS[],FILE *wf){
    int currTime = taskList[0].arrivalTime;
    int finishedNum=0;
    int waitTimeSum=0;
    float averageWaitTime=0;

    while(finishedNum<taskNum){
        strcpy(orderedList[finishedNum].taskName,taskList[finishedNum].taskName);
        if(currTime>taskList[finishedNum].arrivalTime){
            orderedList[finishedNum].startTime=currTime;
        }else {
            currTime=taskList[finishedNum].arrivalTime;
            orderedList[finishedNum].startTime=currTime;
        }
        orderedList[finishedNum].endTime=currTime+taskList[finishedNum].burstTime;

        currTime+=taskList[finishedNum].burstTime;
        finishedNum++;
        sortByBurst(taskList,taskNum,currTime,finishedNum);


        //strcpy(orderedList[finishedNum].taskName,taskList[finishedNum].taskName);
        //orderedList[finishedNum].endTime=currTime+taskList[finishedNum].burstTime;
        //currTime += taskList[finishedNum].burstTime;
        //finishedNum++;
    }

    averageWaitTime=AverageWaitTime(orderedList,taskList_BOSS,taskNum,taskNum);
    printTable(orderedList,taskNum,wf);
    fprintf(wf,"Average Wait time is %.2f\n",averageWaitTime);
}

void PSJF(struct OrderedTask orderedList[],struct ComputationTask taskList[],int taskNum,struct ComputationTask taskList_BOSS[],FILE *wf){
    int currTime = taskList[0].arrivalTime;
    int finishedNum=0;
    int waitTimeSum=0;
    struct ComputationTask currentTask = taskList[0];
    struct ComputationTask pTask;
    pTask=taskList[0];

    //keep track of how many steps we process to finish.
    int totalRun=0;
    //put the first task into our ordered list.
    strcpy(orderedList[totalRun].taskName,currentTask.taskName);

    while(finishedNum<taskNum){
        //for(int i=0;i<10;i++){
        currTime++;
        currentTask.burstTime--;
        taskList[finishedNum].burstTime--;
        if(currentTask.burstTime==0){
            finishedNum++;
        }
        sortByBurst(taskList,taskNum,currTime,finishedNum);
        //printf("Before:%s , %s\n",currentTask.taskName,pTask.taskName);
        //printf("finished number:%d \n",finishedNum);
        pTask=taskList[finishedNum];
        //pTask=taskList[];
        //printf("After:%s , %s\n",currentTask.taskName,pTask.taskName);
        //printf("%d\n",strcmp(currentTask.taskName,pTask.taskName));
        if(strcmp(currentTask.taskName,pTask.taskName)==0){

            //Task maintain unchanged.
        }else{
            if(totalRun<taskNum){
                //if current task changed, save current task into ordered list.
                strcpy(orderedList[totalRun].taskName,currentTask.taskName);
                orderedList[totalRun].endTime=currTime;
                orderedList[totalRun].startTime=currentTask.arrivalTime;
                totalRun++;
                //change task
                currentTask=pTask;
                currentTask.arrivalTime=currTime;

            }else{
                orderedList = realloc(orderedList,(taskNum+1)*sizeof(struct OrderedTask));
                strcpy(orderedList[totalRun].taskName,currentTask.taskName);
                orderedList[totalRun].endTime=currTime;
                orderedList[totalRun].startTime=currentTask.arrivalTime;
                //finishedNum++;
                totalRun++;
                //change task
                currentTask=pTask;
                currentTask.arrivalTime=currTime;

            }
        }

    }
    //float averageWaitTime=(float)(totalWaitingTimePSJF(orderedList,taskList_BOSS,totalRun,taskNum))/(float)taskNum;
    float averageWaitTime =AverageWaitTime(orderedList,taskList_BOSS,totalRun,taskNum);
    //printf("PSJF:\n");
    printTable(orderedList,totalRun,wf);
    fprintf(wf,"Average Wait time is %.2f\n",averageWaitTime);
}


void sortByBurst(struct ComputationTask taskList[],int taskNum,int currTime,int finishedNum)
{
    int i;
    int j;
    for(i=finishedNum;i<taskNum;i++){
        if(taskList[i].arrivalTime<currTime){
            for(j=finishedNum;j<taskNum-i;j++){
                if(taskList[j].burstTime>taskList[j+1].burstTime){
                    struct ComputationTask temp = taskList[j];
                    //printf("switching %d and %d",j,(j+1));
                    taskList[j]=taskList[j+1];
                    taskList[j+1]=temp;
                }
            }
        }
    }
}


float AverageWaitTime(struct OrderedTask orderedList[],struct ComputationTask taskList[],int oNum,int tNum){
    int totalWaitTime=0;

    int i;
    for(i=0;i<tNum;i++){
        //printf("Now is the task %s",taskList[i].taskName);
        int latestEndTime = 0;
        int latestStartTime=0;
        int j;
        for(j=0;j<oNum;j++){
            if(strcmp(orderedList[j].taskName,taskList[i].taskName)==0){
                latestStartTime = orderedList[j].startTime;
                latestEndTime = orderedList[j].endTime;
            }
        }
        totalWaitTime+=(latestStartTime-(taskList[i].burstTime-(latestEndTime-latestStartTime))-taskList[i].arrivalTime);
    }
    float averageWaitTime=(float)totalWaitTime/(float)tNum;
    return averageWaitTime;
}







