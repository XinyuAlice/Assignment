#include <stdio.h>
#include <stdlib.h>

int test(int M);

int N;
int *X, *Y, *Z;

int main() {
	scanf("%d", &N);
	
	X = (int *) malloc(N * sizeof(int));
	Y = (int *) malloc(N * sizeof(int));
	Z = (int *) malloc(N * sizeof(int));
	
	for (int i = 0; i < N; i++) {
		scanf("%d %d %d", &X[i], &Y[i], &Z[i]);
	}
	
	int L = 0;
	int H = 1733;
	
	while (L < H - 1) {
		if (test((L + H) / 2)) {
			H = (L + H) / 2;
		} else {
			L = (L + H) / 2;
		}
	}
	
	printf("%i\n", H);
	
	return(0);
}

int test(int M) {
	int Q[N];
	
	for (int i = 0; i < N; i++) {
		Q[i] = i;
	}
	
	int qsize = 1;
	
	for (int i = 0; i < qsize; i++) {
		for (int j = qsize; j < N; j++) {
			if (M * M >=
				(X[Q[i]] - X[Q[j]]) * (X[Q[i]] - X[Q[j]]) +
				(Y[Q[i]] - Y[Q[j]]) * (Y[Q[i]] - Y[Q[j]]) +
				(Z[Q[i]] - Z[Q[j]]) * (Z[Q[i]] - Z[Q[j]])) {
				int temp = Q[j];
				Q[j] = Q[qsize];
				Q[qsize] = temp;
				qsize++;
			}
		}
	}
	
	return(qsize == N);
}