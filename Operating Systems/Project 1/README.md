# OS Assignment 1
Project created by Alex Kempen.

# Build
This project is written in Java and developed in vs-code.

# Tests
Tests are written using JUnit 5. To run the tests, install JUnit 5, add it to the appropriate library, then use the built in test-runner in vs-code.
### CommandTest.java
Tests for verifying the functionality of CommandInvoker and CommandReceiver.

### MainTest.java
Parameterized tests which run my test cases as well as the sample test cases automatically using a simplified memory.

### MemoryTest.java
A test which verifies the operation of memory parsing.

### TimerTest.java
A test which verifies the poll method of Timer.

# Files
## cpu
### Cpu.java
The main Cpu function. Executes the program using an InstructionHandler and manages the Timer.

### CpuFactory.java
A factory for generating a Cpu. Includes facilities for setting the seed and output in order to facilitate testing.

### Instruction.java
An enum defining the possible instructions. Includes methods for converting to and from integers.

### InstructionHandler.java
A class which exposes methods for fetching and executing instructions on a MemoryInterface.

### OperatingMode.java
An enum defining the possible operating modes - user or kernel.

### Register.java
An enum defining possible registers.

### Registers.java
A class which manages and exposes registers.

### Timer.java
A class which exposes a timer. The timer may be polled and returns true when an interrupt should be called.

## memory
### Memory.java
A MemoryInterface implementing a basic memory object.

### MemoryFactory.java
A factory which can be used to create a MemoryInterface. Supports creating a basic Memory object (which is useful for testing), or
a MemoryManager. Note the Memory initialized in MemoryManager is passed across the serial bus to the CommandProcess when CommandInvoker
is initialized, and is thus not kept in memory.

### MemoryInterface.java
A generic interface for a memory unit. Used by Cpu to define interactions with memory in a generic way. Implementing memory this 
way enables Cpu to be easily tested with a basic Memory object while still allowing main to run with a full-on MemoryManager.

## MemoryManager.java
A class which implements Manager and is used to pass Commands to a CommandInvoker. 

## memory/command
### BatchWriteCommand.java
A Command for writing an entire memory space at once.

### ReadCommand.java
A ReadCommand for reading a value from memory.

### WriteCommand.java
A Command for writing a value to memory.

## command
A set of files for creating and managing processes which can be interacted with using the Command Design Pattern.
This implementation is generic, meaning the class is agnostic to any given executor and manager combination.

### Command.java
A class defining a basic command object.

### ReadCommand.java
A class defining a command object which is capable of returning a value to its owner. ReadCommand does not extend
Command since the return type of Command cannot be overridden. CommandReceiver and CommandInvoker automatically
pass the return value back to the caller.

### CommandInvoker.java
A class which sends Commands to a CommandStream and receives the result value where applicable. Generic overloads
are used to automatically provide the result of ReadCommands back to the caller.

Although CommandInvoker takes an Executor as an argument, note how it is passed immediately to the CommandStream;
thus, the CommandInvoker does not actually assume ownership of the Executor, instead delegating it to the CommandReceiver
running inside the CommandProcess.

Direct injection is utilized to keep the CommandInvoker agnostic to any specific implementation, which enables easier testing.

### CommandReceiver.java
The counterpart to CommandInvoker which runs in a CommandProcess. During initialization, the CommandReceiver receives an
Executor from the CommandInvoker and assumes ownership of it. The Executor is then passed to future Commands which are passed
to it, enabling them to operate on it. This way, CommandReceiver can be implemented in a completely generic way.

Note how direct injection of command stream is once again utilized to keep the CommandReceiver agnostic.

### CommandStream.java
A class defining a set of Object streams which may be used to pass data. Since ObjectOutputStreams must be initialized first,
these are taken in the constructor. A set method is used to add the ObjectInputStream since some tests wish to perform additional
logic between the Command initializations.

### Executor.java
A simple class which adds serialization semantics in order to define a class which is passed across a CommandStream to a CommandReceiver
and then operated on by future Commands.

### ExitCommand.java
A command which can be used to terminate a CommandProcess.

### Manager.java
A class which wraps a CommandInvoker, enabling easy usage of its functionality.

### ProcessUtils.java
Utils for starting and working with processes. compile uses a static value to avoid redundant compilations.