import React, { useState, useEffect } from 'react';
import { createChatRoom, getChatRoom, updateChatRoom } from '../api/chatApi';
import { useNavigate, useParams } from 'react-router-dom';

const ChatRoomForm = ({ isEdit = false }) => {
    const [form, setForm] = useState({ roomName: '', userName: '', aiName: '', topic: '' });
    const navigate = useNavigate();
    const { id } = useParams();

    useEffect(() => {
        if (isEdit && id) {
            getChatRoom(id).then(res => setForm(res.data));
        }
    }, [isEdit, id]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setForm({ ...form, [name]: value });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        const action = isEdit ? updateChatRoom(id, form) : createChatRoom(form);
        action.then(() => navigate('/'));
    };

    return (
        <div className="max-w-xl mx-auto mt-10 p-6 bg-white shadow rounded-lg">
            <h2 className="text-xl font-bold mb-4">{isEdit ? '채팅방 수정' : '채팅방 생성'}</h2>
            <form onSubmit={handleSubmit} className="space-y-4">
                <input name="roomName" value={form.roomName} onChange={handleChange}
                       placeholder="채팅방 이름" className="w-full border p-2 rounded" required />
                <input name="userName" value={form.userName} onChange={handleChange}
                       placeholder="사용자 이름" className="w-full border p-2 rounded" required />
                <input name="aiName" value={form.aiName} onChange={handleChange}
                       placeholder="챗봇 이름" className="w-full border p-2 rounded" required />
                <select name="topic" value={form.topic} onChange={handleChange}
                        className="w-full border p-2 rounded" required>
                    <option value="">챗봇 분야 선택</option>
                    <option value="트럼프 대통령">트럼프 대통령</option>
                    <option value="젠슨 황">젠슨 황</option>
                    <option value="손흥민">손흥민</option>
                    <option value="연인">연인</option>
                    <option value="국회의원">국회의원</option>
                    <option value="치킨집 사장님">치킨집 사장님</option>
                    <option value="상담가">상담가</option>
                </select>
                <button type="submit"
                        className="w-full bg-blue-500 hover:bg-blue-600 text-white py-2 px-4 rounded">
                    {isEdit ? '수정하기' : '생성하기'}
                </button>
            </form>
        </div>
    );
};

export default ChatRoomForm;
