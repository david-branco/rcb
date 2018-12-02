cmake_minimum_required(VERSION 3.5)
project(Reliable_Causal_Broadcast)

set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -Wall")

set(SOURCE_FILES rcb)
add_executable(Reliable_Causal_Broadcast ${SOURCE_FILES})