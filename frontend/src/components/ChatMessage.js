import React, { useEffect, useRef, useState } from 'react';
import { getChatRoom } from '../api/chatApi';
import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';
import { useParams } from 'react-router-dom';

let stompClient = null;

const ChatMessage = () => {
    const { id } = useParams();
    const [room, setRoom] = useState(null);
    const [messages, setMessages] = useState([]);
    const [input, setInput] = useState('');
    const messagesEndRef = useRef(null);

    const connect = () => {
        const socket = new SockJS('http://localhost:8080/ws');
        const client = new Client({
            webSocketFactory: () => socket,
            onConnect: () => {
                client.subscribe(`/topic/chat/${id}`, (msg) => {
                    const message = JSON.parse(msg.body);
                    setMessages((prev) => [...prev, message]);
                });
            },
        });
        client.activate();
        stompClient = client;
    };

    const sendMessage = () => {
        if (stompClient && stompClient.connected) {
            stompClient.publish({
                destination: `/app/chat.send`,
                body: JSON.stringify({
                    roomId: id,
                    sender: 'user',
                    content: input,
                }),
            });
            setInput('');
        } else {
            console.error('❌ STOMP 연결되지 않음');
        }
    };

    useEffect(() => {
        getChatRoom(id).then((res) => setRoom(res.data));
        connect();
    }, [id]);

    useEffect(() => {
        messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
    }, [messages]);

    if (!room) return <div className="text-center mt-10 text-gray-500">로딩중...</div>;

    return (
        <div className="max-w-2xl mx-auto mt-10 p-6 bg-white shadow-md rounded-lg">
            <h2 className="text-xl font-semibold mb-4 text-center text-blue-600">
                {room.roomName} <span className="text-gray-500">with {room.aiName}</span>
            </h2>

            <div className="border rounded h-80 overflow-y-scroll p-4 bg-gray-50 space-y-2">
                {messages.map((msg, i) => (
                    <div key={i} className={`flex ${msg.sender === 'user' ? 'justify-end' : 'justify-start'}`}>
                        <div
                            className={`max-w-xs px-4 py-2 rounded-lg text-sm ${
                                msg.sender === 'user'
                                    ? 'bg-blue-500 text-white'
                                    : 'bg-gray-200 text-gray-800'
                            }`}
                        >
                            <strong className="block mb-1 text-xs">
                                {msg.sender === 'user' ? room.userName : room.aiName}
                            </strong>
                            {msg.content}
                        </div>
                    </div>
                ))}
                <div ref={messagesEndRef} />
            </div>

            <div className="mt-4 flex gap-2">
                <input
                    type="text"
                    value={input}
                    onChange={(e) => setInput(e.target.value)}
                    className="flex-grow border border-gray-300 rounded px-3 py-2 focus:outline-none focus:ring focus:border-blue-300"
                    placeholder="메시지를 입력하세요..."
                />
                <button
                    onClick={sendMessage}
                    className="bg-blue-500 hover:bg-blue-600 text-white px-4 py-2 rounded"
                >
                    전송
                </button>
            </div>
        </div>
    );
};

export default ChatMessage;
