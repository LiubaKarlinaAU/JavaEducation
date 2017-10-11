CC	?= gcc
CFLAGS	?= -Wall -Wextra -Werror -pedantic
LFLAGS	?= -lpthread

SRC	:= once.c rwlock.c tas.c
EXE	:= $(patsubst %.c,%,$(SRC))

all: $(EXE)

$(EXE): %: %.c
	$(CC) $(CFLAGS) $< $(LFLAGS) -o $@

.PHONY: clean
clean:
	rm -rf $(EXE)
