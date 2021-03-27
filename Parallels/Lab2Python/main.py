import threading
import random
from time import sleep
from time import time
from queue import Queue


class CPUQueue:

    def __init__(self, queue_id):
        self.queue = Queue()
        self.sync = threading.Condition()
        self.max_len = 0
        self.queue_id = queue_id

    def is_empty(self):
        return self.queue.empty()

    def put(self, element):

        try:
            self.sync.acquire()

            self.queue.put(element)
            print(f'Element {element} is added to the queue{self.queue_id}.')

            self.print_info()
            self.max_len = self.queue.qsize() if self.queue.qsize() > self.max_len else self.max_len
        finally:
            self.sync.notify()
            self.sync.release()

    def get(self):

        try:
            self.sync.acquire()
            if self.is_empty():
                print('Queue is EMPTY. Cannot get elements. Waiting...')
                self.sync.wait()

            temp = self.queue.get()
            print(f'Element {temp} is removed from the queue{self.queue_id}.')

            self.print_info()
            return temp
        finally:
            self.sync.notify()
            self.sync.release()

    def print_info(self):
        print(f'QUEUE{self.queue_id}: {self.queue.queue}')

    def get_size(self):
        return self.queue.qsize()


class ProcessGenerator(threading.Thread):

    def __init__(self, queue1, queue2, num, C1, C2):
        threading.Thread.__init__(self)
        self.queue1 = queue1
        self.queue2 = queue2
        self.num = num
        self.C1 = C1
        self.C2 = C2
        self.i = 1

    def run(self):
        for i in range(1, self.num + 1):
            delay = random.randint(1, 20) / 100
            sleep(delay)
            print(f'GENERATOR: Process {i} generated with delay {delay}\n')
            if not self.C1.is_busy():
                print(f'TO PROCESSOR {self.C1.cpu_id} ->')
                self.C1.process(i)
            elif not self.C2.is_busy():
                print(f'TO PROCESSOR {self.C2.cpu_id} ->')
                self.C2.process(i)
            elif self.C1.is_busy and self.C2.is_busy:
                    if queue1.get_size() <= queue2.get_size():
                        self.queue1.put(i)
                    else:
                        self.queue2.put(i)

            # if not self.C1.is_busy():
            #     print(f'TO PROCESSOR {self.C1.cpu_id} ->')
            #     self.C1.process(i)
            # elif not self.C2.is_busy():
            #     print(f'TO PROCESSOR {self.C2.cpu_id}->')
            #     self.C2.process(i)
            # elif queue1.get_size() <= queue2.get_size():
            #     self.queue1.put(i)
            # else:
            #     self.queue2.put(i)


class CPU(threading.Thread):

    def __init__(self, queue, cpu_id):
        threading.Thread.__init__(self)
        self.queue = queue
        self.busy = False
        self.flow_time = 0
        self.id = 0
        self.cpu_id = cpu_id
        self.end_time = 0

    def is_busy(self):
        return self.busy

    def run(self):
        end_time = time()
        start_time = time()
        while True:
            if self.busy:
                end_time = time()
                print(f'Is processing without queue...')
                processing_time = random.randint(10, 40) / 100
                sleep(processing_time)
                print(f'Without QUEUE: Process {self.id} processed in time {processing_time}.\n')
                self.busy = False
                end_time = time()

            if self.busy == False and self.queue.is_empty() == False:
                end_time = time()
                self.busy = True
                processing_time = random.randint(10, 40) / 100
                self.id = self.queue.get()
                sleep(processing_time)
                print(f'CPU{self.cpu_id}: Process {self.id} processed in time {processing_time}.\n')
                self.busy = False
                end_time = time()

            if time() - end_time > 1:
                break

    def process(self, id):
        self.busy = True
        self.id = id


num_of_processes = 100

queue1 = CPUQueue(1)
queue2 = CPUQueue(2)
C1 = CPU(queue1, 1)
C2 = CPU(queue2, 2)
generator = ProcessGenerator(queue1, queue2, num_of_processes, C1, C2)

print(f'Will be generated {num_of_processes} processes\n')
generator.start()
C1.start()
C2.start()

generator.join()
C1.join()
C2.join()

print(f'Max queue1 size = {queue1.max_len}.')
print(f'Max queue2 size = {queue2.max_len}.')