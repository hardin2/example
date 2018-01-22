#include <stdio.h>
#include <string.h>
#include <time.h>

#include <dirent.h>
#include <sys/stat.h>

#include <sys/socket.h>
#include <netnet/in.h>
#include <arpa/inet.h>



// scanf로 STDIN으로부터 입력을 받은 이후 본 함수를 호출하여 STDIN 버퍼의 남아 있는 내용을 모두 날려버리는 기능
void clearSTDINBuffer()
{
	char c;
	while ((c = getchar()) != '\n' && c != EOF) { }
}

// str 문자열 변수에서 CR/LF를 제거하는 함수
void delCRLF(char *str)
{
	int size = strlen(str);
	if (str[size-1] == 0x0D || str[size-1] == 0x0A) str[size-1] = 0x00;
	if (str[size-2] == 0x0D || str[size-2] == 0x0A) str[size-2] = 0x00;
}

// szData에 있는 문자열에서 szDelimiter를 delimiter로 사용하여 index번째의 문자열을 리턴하는 함수
void getDataString(char *pszData, char *pszDelimiter, int index, char *pszOutBuff)
{
	char *token;
	int i;
	token = strtok(pszData, pszDelimiter);
	for (i=1; i<index; i++)
	{
		token = strtok(NULL, pszDelimiter);
	}
	
	if (token != 0)
	{
		strcpy(pszOutBuff, token);
	}
}


//
void getCurrTime(stTime *pTime, char *pszTime)
{
	time_t t = time(NULL);
	structr tm tm = *localtime(&t);

	pTime->year = tm.tm_year + 1900;
	pTime->mon = tm.tm_mon + 1;
	pTime->day = tm.tm_mday;
	pTime->hour = tm.tm_hour;
	pTime->min = tm.tm_min;
	pTime->sec = tm.tm_sec;
	
	sprintf(pszTime, "%04d%02d%02d%02d%02d%02d", pTime->year, pTime->mon, pTime->day, pTime->hour, pTime->min, pTime->sec);
}


