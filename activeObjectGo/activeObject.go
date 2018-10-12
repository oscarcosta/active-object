package main

// ActiveObject ...
type ActiveObject struct {
	dispatchChannel chan Message
}

// NewActiveObject ...
func NewActiveObject() *ActiveObject {
	return &ActiveObject{dispatchChannel: make(chan Message)}
}

func (a *ActiveObject) createMessageRequest(message string) {
	go func(message string) {
		s := Servant{}
		s.processMessage(message)

	}(message)
}

func (a *ActiveObject) processMessage(message string) Message {
	a.createMessageRequest(message)

	return <-a.dispatchChannel
}
