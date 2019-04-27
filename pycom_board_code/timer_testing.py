import machine



def timerResponse(alarm):
    global seconds
    seconds += 1
    if(seconds == 10):
        print("Time's up")
        alarm.cancel() #end timer after 10 seconds
    else:
        print("seconds ", seconds)

seconds = 0
tim = machine.Timer.Alarm(handler= timerResponse, s=1, periodic=True)

#while(True):
#    print(0)
#print(tim)
