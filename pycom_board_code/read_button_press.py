from machine import Pin
import time
# initialize GP17 in gpio mode and make it an input with the # pull-up enabled

Pin.exp_board.G17
p_in = Pin(Pin.exp_board.G17, mode=Pin.IN, pull = Pin.PULL_UP)
Pin.exp_board.G17.id()

#p_in = Pin('GP17', mode=Pin.IN, pull=Pin.PULL_UP)

p_in()

while True:
    if p_in() == 0:
        print("Button pressed", p_in())
        time.sleep(1)
    else:
        pass
