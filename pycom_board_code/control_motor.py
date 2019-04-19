from machine import Pin
import time


# initialize `P9` in gpio mode and make it an output
Pin.exp_board.G9
p_out = Pin(Pin.exp_board.G9, mode=Pin.OUT)
Pin.exp_board.G9.id()

for i in range(1,4):
    p_out.value(1)
    time.sleep(2)
    p_out.value(0)
    time.sleep(2)
