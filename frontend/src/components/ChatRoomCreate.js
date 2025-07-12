import React, { useState } from 'react';
import { createChatRoom } from '../api/chatApi';
import { useNavigate } from 'react-router-dom';

const ChatRoomCreate = () => {
    const [form, setForm] = useState({
        roomName: '',
        userName: '',
        aiName: '',
        topic: '연인',
    });

    const navigate = useNavigate();

    const handleChange = (e) => {
        setForm({ ...form, [e.target.name]: e.target.value });
    };

    const handleSubmit = async () => {
        const res = await createChatRoom(form);
        navigate(`/chat/${res.data.id}`);
    };

    return (
        <div>
            <h2>채팅방 생성</h2>
            <input name="roomName" placeholder="채팅방 이름" onChange={handleChange} />
            <input name="userName" placeholder="내 이름" onChange={handleChange} />
            <input name="aiName" placeholder="챗봇 이름" onChange={handleChange} />
            <select name="topic" onChange={handleChange}>
                <option value="트럼프 대통령">트럼프 대통령</option>
                <option value="젠슨 황">젠슨 황</option>
                <option value="손흥민">손흥민</option>
                <option value="연인">연인</option>
                <option value="국회의원">국회의원</option>
                <option value="치킨집 사장님">치킨집 사장님</option>
                <option value="상담가">상담가</option>
            </select>
            <button onClick={handleSubmit}>채팅방 만들기</button>
        </div>
    );
};

export default ChatRoomCreate;
