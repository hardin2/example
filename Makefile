.PHONY : util
.SUFFIXES : .c .o

###################################
# 최종 실행파일명
TARGET = test.exe

# 소스 파일 추가시 OBJS에 .o 확장자로 추가할것
# OBJS = main.o file1.o file2.o
# OBJS += ../util/SHA256/SHA256.o
# OBJS += ../util/util.o
OBJS = main.o
###################################

# CFLAGS : compile options (.c ==> .o)
# LDFLAGS : linking opotions (.o ==> .exe)
CC = gcc
CFLAGS = -pthread
CFLAGS += -Wall
LD = gcc
LDFLAGS = -pthread

###################################
# gdb 사용을 위한 디버깅 옵션
CFLAGS += -g -O0
#CFLAGS += -O3

# 기타 추가하고 싶은 옵션...
#CFLAGS +=
#LDFLAGS +=
###################################


# 기본 제공되는 라이브러리에 대한 설정
UTIL_DIR = ../util
CFLAGS += -I$(UTIL_DIR)/include -I$(UTIL_DIR)/SHA256
LDFLAGS += -L$(UTIL_DIR)/lib
LDFLAGS += -lgthread-2.0 -lgio-2.0 -lgobject-2.0 -lglib-2.0 -lintl

# 기본 build rule 설정
all: $(TARGET)

$(TARGET) : $(OBJS)
	$(LD) -o $(TARGET) $(OBJS) $(LDFLAGS)

clean:
	rm -f $(TARGET)
	rm -f $(OBJS)

.c.o :
	$(CC) -c $< -o $@ $(CFLAGS)

