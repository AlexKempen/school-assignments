
To facilitate effecient communication between sub-processes and the Operating System, I choose to implement the command design pattern. In this pattern, instructions are represented as classes and passed an executable object they can interact with, and various generic utilities are created for initializing and retrieving them. Furthermore, the classes use Java's Serializable feature to enable serializing the entire command class at once, enabling a clean transfer of data between processes.

The cpu has ownership of the registers and is responsible for fetching and executing instructions from memory. Notably, the cpu and memory are siloed from each other, with the actual communication done via Commands managed by the operating system. The operating system interfaces with each subsystem through the respective Manager, with the manager providing important facilities like exceptions and other behaviors.

