package main

// Servant ...
type Servant struct {
}

func (s *Servant) processMessage(message string) Message {
	return Message{content: "The message was received. Original message: " + message}
}
