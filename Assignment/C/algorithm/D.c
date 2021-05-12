#include <stdio.h>

int main() {
	int D[4096];

	D[0] = 0;	
	for (int i = 1; i < 4096; i++) {
		D[i] = -1;
	}
	
	int Q[4096];
	Q[0] = 0;
	int qhead = 0;
	int qtail = 1;
	
	while (qhead < qtail) {
		int h = Q[qhead++];
		int d = D[h];
		int t;		
		
		for (int v = 0; v < 16; v++) {
			t = h | v;
			if (D [t] == -1) {
				D[t] = d + 1;
				Q[qtail++] = t;
			}
		}
		
		for (int amt = 0; amt < 8; amt++) {
			t = (h << amt) & ((1 << 12) - 1);
			if (D [t] == -1) {
				D[t] = d + 1;
				Q[qtail++] = t;
			}
		}

		for (int amt = 0; amt < 8; amt++) {
			t = (h >> amt) | ((h & ((1 << amt) - 1)) << (12 - amt));
			if (D [t] == -1) {
				D[t] = d + 1;
				Q[qtail++] = t;
			}
		}
		
		t = (h + 1) & ((1 << 12) - 1);
		if (D [t] == -1) {
			D[t] = d + 1;
			Q[qtail++] = t;
		}		
				
		t = (~h) & ((1 << 12) - 1);
		if (D [t] == -1) {
			D[t] = d + 1;
			Q[qtail++] = t;
		}		
		
		for (int amt = 0; amt < 8; amt++) {
			t = (h + (h << amt)) & ((1 << 12) - 1);
			if (D [t] == -1) {
				D[t] = d + 1;
				Q[qtail++] = t;
			}
		}		
	}

	int T;	
	scanf("%d", &T);
	
	for (int i = 0; i < T; i++) {
		int g;
		scanf("%d", &g);
		printf("%i\n", D[g]);
	}
	
	return(0);
}