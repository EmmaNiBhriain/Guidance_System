from machine import Pin
import time


###############################################
# Toggle the vibrating disk motor on and off  #
###############################################
Pin.exp_board.G9
p_out = Pin(Pin.exp_board.G9, mode=Pin.OUT)
Pin.exp_board.G9.id()

for i in range(1,4):
    p_out.value(1)
    time.sleep(2)
    p_out.value(0)
    time.sleep(2)
