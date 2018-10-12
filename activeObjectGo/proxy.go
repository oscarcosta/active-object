package main

// Proxy ...
type Proxy interface {
	processMessage(message string) Message
}
