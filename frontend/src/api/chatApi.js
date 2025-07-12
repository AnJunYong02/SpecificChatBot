// api/chatApi.js
import axios from 'axios';

const API_BASE = 'http://localhost:8080/api/chatroom';

export const fetchChatRooms = () => axios.get(API_BASE);
export const createChatRoom = (roomData) => axios.post(API_BASE, roomData);
export const getChatRoom = (id) => axios.get(`${API_BASE}/${id}`);
export const updateChatRoom = (id, data) => axios.put(`${API_BASE}/${id}`, data);
export const deleteChatRoom = (id) => axios.delete(`${API_BASE}/${id}`);
