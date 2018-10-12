package main

import (
	"fmt"

	log "github.com/Sirupsen/logrus"
)

func main() {
	var proxy Proxy = NewActiveObject()

	for i := 0; i < 10; i++ {
		m := proxy.processMessage(fmt.Sprintf("Message #%d", i))
		log.Info(m.content)
	}
}
