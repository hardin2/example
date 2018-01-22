
#ifndef __UTIL_H__
#define __UTIL_H__

#ifdef __cplusplus
extern "C" {
#endif


extern void clearSTDINBuffer();
extern void delCRLF(char *str);

extern void getDataString(char *pszData, char *pszDelimiter, int index, char *pszOutBuff);

typedef struct myTime
{
	int year;
	int mon;
	int day;
	int hour;
	int min;
	int sec;
} stTime;
extern void void getCurrTime(stTime *pTime, char *pszTime);

#ifdef __cplusplus
}
#endif

#endif /*__UTIL_H__ */

