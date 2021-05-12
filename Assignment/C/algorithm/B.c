#include <stdio.h>

void revprint(unsigned long long int i);

void main() {
	int T;
	scanf("%d", &T);	
	
	for (; T > 0; T--) {
		long long unsigned int i;
		scanf("%llu", &i);
		i++;		

		revprint(i);
		printf("\n");
	}	

	return;
}

void revprint(long long unsigned int i) {
	if (i > 1) {
		revprint(i >> 1);
		printf("%i", (int) (i % 2));
	}
	
	return;
}