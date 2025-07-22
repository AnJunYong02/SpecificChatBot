import React, { useEffect, useState } from 'react';
import { fetchChatRooms, deleteChatRoom } from '../api/chatApi';
import { useNavigate } from 'react-router-dom';

const ChatRoomList = () => {
    const [rooms, setRooms] = useState([]);
    const navigate = useNavigate();

    const loadRooms = () => {
        fetchChatRooms().then(res => {
            console.log("✅ fetch 응답 데이터:", res.data);  // 🔍 여기에 room.id가 존재하는지 확인
            setRooms(res.data);
        });
    };


    const handleDelete = (id) => {
        console.log("삭제 요청 아이디 : ", id);
        if (window.confirm('정말 삭제하시겠습니까?')) {
            deleteChatRoom(id).then(() => loadRooms());
        }
    };

    const handleEdit = (id) => {
        navigate(`/edit/${id}`);
    };

    useEffect(() => {
        loadRooms();
    }, []);

    return (
        <div className="max-w-3xl mx-auto mt-10 p-6 bg-white shadow rounded-lg">
            <h2 className="text-2xl font-semibold mb-4">채팅방 목록</h2>
            <button
                onClick={() => navigate('/create')}
                className="mb-4 bg-blue-500 hover:bg-blue-600 text-white py-2 px-4 rounded"
            >
                채팅방 생성
            </button>
            <ul className="space-y-4">
                {rooms.map(room => (
                    <li key={room.id} className="p-4 border rounded flex justify-between items-center">
                        <div>
                            <strong>{room.roomName}</strong> (AI: {room.aiName})
                        </div>
                        <div className="space-x-2">
                            <button
                                onClick={() => navigate(`/chat/${room.id}`)}
                                className="bg-blue-500 hover:bg-blue-600 text-white px-3 py-1 rounded"
                            >
                                입장
                            </button>
                            <button
                                onClick={() => handleEdit(room.id)}
                                className="bg-yellow-400 hover:bg-yellow-500 text-white px-3 py-1 rounded"
                            >
                                수정
                            </button>
                            <button
                                onClick={() => handleDelete(room.id)}
                                className="bg-red-500 hover:bg-red-600 text-white px-3 py-1 rounded"
                            >
                                삭제
                            </button>
                        </div>
                    </li>
                ))}
            </ul>

        </div>
    );
};

export default ChatRoomList;
