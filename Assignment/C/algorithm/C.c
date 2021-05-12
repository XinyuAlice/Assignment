#include <stdio.h>
#include <stdlib.h>

int N, M;
long long int *h_ptr;
long long int *w_ptr;

int hComp(const void *ptr1, const void *ptr2) {
   return (h_ptr[*(int *) ptr1] - h_ptr[*(int *) ptr2]);
}

void fill(int i, long long int m) {
	w_ptr[i] = m;
	
	if ((w_ptr[i - (M + 2)] == -1) && (h_ptr[i - (M + 2)] <= m)) {
		fill(i - (M + 2), m);
	}

	if ((w_ptr[i - 1] == -1) && (h_ptr[i - 1] <= m)) {
		fill(i - 1, m);
	}

	if ((w_ptr[i + 1] == -1) && (h_ptr[i + 1] <= m)) {
		fill(i + 1, m);
	}
	
	if ((w_ptr[i + (M + 2)] == -1) && (h_ptr[i + (M + 2)] <= m)) {
		fill(i + (M + 2), m);
	}
	
	return;
}

int main() {	
	scanf("%d %d", &N, &M);
	
	long long int h[N + 2][M + 2];
	long long int w[N + 2][M + 2];
	
	for (int j = 0; j <= M + 1; j++) {
		h[0][j] = -1;
		w[0][j] = 0;
	}
	
	for (int i = 1; i <= N; i++) {
		h[i][0] = -1;
		w[i][0] = 0;
		for (int j = 1; j <= M; j++) {
			scanf("%lld", &h[i][j]);
			w[i][j] = -1;
		}
		h[i][M + 1] = -1;
		w[i][M + 1] = 0;
	}

	for (int j = 0; j <= M + 1; j++) {
		h[N + 1][j] = -1;
		w[N + 1][j] = 0;
	}

	h_ptr = (long long int *) h;
	w_ptr = (long long int *) w;
	
	int r[(N + 2) * (M + 2)];
	for (int i = 0; i < (N + 2) * (M + 2); i++) {
		r[i] = i;
	}
	
	qsort(r, (N + 2) * (M + 2), sizeof(int), hComp);

	for (int i = 0; i < (N + 2) * (M + 2); i++) {
		if (h_ptr[r[i]] >= 0) {
			if ((w_ptr[r[i] - (M + 2)] >= 0) ||
				(w_ptr[r[i] - 1] >= 0) ||
				(w_ptr[r[i] + 1] >= 0) ||
				(w_ptr[r[i] + (M + 2)] >= 0)) {
				fill(r[i], h_ptr[r[i]]);
			}
		}
	}

	for (int i = 1; i <= N; i++) {
		for (int j = 1; j < M; j++) {
			printf("%lli ", w[i][j] - h[i][j]);
		}
		printf("%lli\n", w[i][M] - h[i][M]);
	}
	
	return(0);
}