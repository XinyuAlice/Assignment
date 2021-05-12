#include <stdio.h>

#define MAX(a,b) (a > b ? a : b)

int main() {
	int N;
	scanf("%d", &N);
	
	int H[N];
	int W[N];
	int PS[N + 1];

	PS[0] = 0;	
	for (int i = 0; i < N; i++) {
		scanf("%i %i", &H[i], &W[i]);
		PS[i + 1] = PS[i] + W[i];
	}
	
	int Stack[N];
	int size = 0;
	
	int L[N];
	
	for (int i = 0; i < N; i++) {
		while ((size > 0) && (H[Stack[size - 1]] >= H[i])) {
			size--;
		}
				
		if (size > 0) {
			L[i] = PS[Stack[size - 1] + 1];
		} else {
			L[i] = 0;
		}
		
		Stack[size++] = i;
	}
	
	size = 0;	
	
	int R[N];
	
	for (int i = N - 1; i >= 0; i--) {
		while ((size > 0) && (H[Stack[size - 1]] >= H[i])) {
			size--;
		}
		
		if (size > 0) {
			R[i] = PS[Stack[size - 1]];
		} else {
			R[i] = PS[N];
		}
		
		Stack[size++] = i;
	}
	
	long long int price = 0;
	
	for (int i = 0; i < N; i++) {
		price = MAX(price, 50 * ((long long int) H[i]) *
			((long long int) (R[i] - L[i])));
	}
	
	printf("%lli\n", price);
	
	return(0);
}