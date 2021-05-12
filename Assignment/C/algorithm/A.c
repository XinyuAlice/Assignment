#include <stdio.h>

#define UNHAPPY	0
#define HAPPY	1
#define WAITING	2
#define BLANK	3

void fillArrays();
int checkStatus(int i);

int status[1000001];
int psum[1000001];

int main() {
	int T, A, B;
	
	fillArrays();
	
	scanf("%d", &T);
	
	for (int i = 0; i < T; i++) {
		scanf("%d %d", &A, &B);
		printf("%i\n", psum[B] - psum[A - 1]);
	}
	
	return(0);
}

void fillArrays() {	
	status[1] = HAPPY;
	for (int i = 2; i <= 1000000; i++) {
		status[i] = BLANK;
	}
	
	psum[0] = 0;
	for (int i = 1; i <= 1000000; i++) {
		psum[i] = psum[i - 1] + (checkStatus(i) == HAPPY);
	}

	return;
}

int checkStatus(int i) {
	if ((status[i] == HAPPY) || (status[i] == UNHAPPY)) {
		return(status[i]);
	} else if (status[i] == WAITING) {
		return(UNHAPPY);
	}

	int j = 0;
	int t = i;
	while (t > 0) {
		j += (t % 10) * (t % 10);
		t = (t - (t % 10)) / 10;
	}
	
	status[i] = WAITING;
	status[i] = checkStatus(j);

	return(status[i]);
}