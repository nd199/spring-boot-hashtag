import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.jsx'
import {ChakraProvider} from "@chakra-ui/react"
import {createStandaloneToast} from '@chakra-ui/toast';
import {createBrowserRouter, RouterProvider} from "react-router-dom";
import './index.css'
import Login from "./components/login/Login.jsx";
import AuthProvider from "./context/AuthContext.jsx";
import ProtectedRoute from "./components/ProtectedRoute.jsx";


const {ToastContainer} = createStandaloneToast()


const router = createBrowserRouter([
    {
        path: "/",
        element: <Login/>
    },
    {
        path: "dashboard",
        element: <ProtectedRoute><App/></ProtectedRoute>
    }
])


ReactDOM
    .createRoot(document.getElementById('root'))
    .render(
        <React.StrictMode>
            <ChakraProvider>
                <AuthProvider>
                    <RouterProvider router={router}/>
                </AuthProvider>
                <ToastContainer/>
            </ChakraProvider>
        </React.StrictMode>,
    )
