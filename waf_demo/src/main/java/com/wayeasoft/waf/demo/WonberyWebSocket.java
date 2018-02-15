/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.wayeasoft.waf.demo;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.PongMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ServerEndpoint("/wonbery/websocket")
public class WonberyWebSocket
{
        private final static Logger log = LoggerFactory.getLogger(WonberyWebSocket.class);

        private static final Set<WonberyWebSocket> connections = new CopyOnWriteArraySet<>();

        private Session session;

        @OnOpen
        public void start(Session session)
        {
                log.debug("open session");
                this.session = session;
                connections.add(this);
                broadcast(" someone get in " + session.getId());
        }

        public static void broadcast(String message)
        {
                log.debug("send message to {} client: {}", connections.size(), message);
                for (WonberyWebSocket client : connections)
                {
                        try
                        {
                                synchronized (client)
                                {
                                        client.session.getBasicRemote().sendText(message + "[" + new DateTime().toString() + "]" );
                                }
                        } catch (IOException e)
                        {
                                log.debug("Chat Error: Failed to send message to client", e);
                                connections.remove(client);
                                try
                                {
                                        client.session.close();
                                } catch (IOException e1)
                                {
                                        // Ignore
                                }
                                broadcast("someone out");
                        }
                }

        }

        @OnMessage
        public void echoTextMessage(Session session, String msg, boolean last)
        {

                System.out.println(last);

                try
                {
                        if (session.isOpen())
                        {
                                session.getBasicRemote().sendText("you just said and echo message to you: " + msg, last);
                        }
                } catch (IOException e)
                {
                        try
                        {
                                session.close();
                        } catch (IOException e1)
                        {
                                // Ignore
                        }
                }
                
                broadcast(session.getId() + " said: " + msg);

                System.out.println("end echo ");
        }

        @OnMessage
        public void echoBinaryMessage(Session session, ByteBuffer bb, boolean last)
        {
                try
                {
                        if (session.isOpen())
                        {
                                session.getBasicRemote().sendBinary(bb, last);
                        }
                } catch (IOException e)
                {
                        try
                        {
                                session.close();
                        } catch (IOException e1)
                        {
                                // Ignore
                        }
                }
        }

        /**
         * Process a received pong. This is a NO-OP.
         *
         * @param pm
         *                Ignored.
         */
        @OnMessage
        public void echoPongMessage(PongMessage pm)
        {
                // NO-OP
        }

        @OnError
        public void onError(Throwable error)
        {
                log.error("", error);
        }

        @OnClose
        public void onClose()
        {
                connections.remove(this);
                String message = "has disconnected.";
                log.debug(message);
                broadcast(message);
        }
}
