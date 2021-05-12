#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main(){
    char *s = (char *) malloc(1001 * sizeof(char));

    printf("Please enter a string of at most 1000 characters: ");
    scanf("%1000s", s);

    int n = strlen(s);

    if (n == 0){
        return(0);
    }

    int pal_start[n];
    pal_start[0] = 0;
    int pal_count = 1;

    /* pal_start stores the starting positions of the
    pal_count palindromic suffixes of the prefix of s
    processed so far */

    printf("s[0..0]: %c\n", s[0]);

    /* s[0] is a single-character palindromic suffix of itself
    ***delete the line above before submitting to leetcode*** */

    for (int i = 1; i < n; i++){
        int new_pal_count = 0;
        for (int j = 0; j < pal_count; j++){
            if ((pal_start[j] > 0) && (s[pal_start[j] - 1] == s[i])){

                /* if s[h..i - 1] is a palindromic suffix of
                s[0..i - 1] and h > 0 and s[h - 1] = s[i]... */

                pal_start[new_pal_count++] = pal_start[j] - 1;

                /* ...then s[h - 1..i] is a palindromic suffix
                of s[0..i] */
            }
        }

        if (s[i - 1] == s[i]){
            pal_start[new_pal_count++] = i - 1;
        }

        /* if s[i - 1] = s[i] then s[i - 1..i] is a
        % palindromic suffix of s[0..i] */

        pal_start[new_pal_count++] = i;
        pal_count = new_pal_count;

        /* s[i] is always a single-character
        palindromic suffix of s[0..i] */

        /* the lines below this print all the
        palindromic suffixes of s[0..i] */

        char temp = s[i + 1];
        s[i + 1] = '\0';

        for (int j = 0; j < pal_count; j++){
            printf("s[%i..%i]: ", pal_start[j], i);
            printf("%s\n", &s[pal_start[j]]);
        }

        s[i + 1] = temp;
    }

    return(0);
}
