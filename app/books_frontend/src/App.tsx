import { BrowserRouter, Routes, Route } from 'react-router-dom';
import './App.css';
import { AuthProvider } from './service/authservice/AuthContext';
import ProtectedRoute from './service/authservice/ProtectedRoute';
import Login from './features/login/Login';
import { AdminPage } from './features/author/pages/AdminPage';
import EditBookPage from './features/book/pages/EditBookPage';
import AdminDashBoard from './features/author/pages/AdminDashboard';
import CreateBookPage from './features/book/pages/CreateBookPage';
import { UserPage } from './features/author/pages/UserPage';
import UserDashBoard from './features/author/pages/UserDashBoard';
import AuthorListingPage from './features/author/pages/AuthorListingPage';
import EditAuthorPage from './features/author/pages/EditAuthorPage';
import CreateAuthorPage from './features/author/pages/CreateAuthorPage';
import AuthorEditBookPage from './features/book/pages/AuthorEditBookPage';
import PublishPage from './features/book/pages/PublishPage';

function App() {

  return (
    <>
      <BrowserRouter>
        <AuthProvider>
          <Routes>
            <Route path="/login" element={<Login />} />

            <Route path="/" element={
              <ProtectedRoute>
                <UserDashBoard />
              </ProtectedRoute>
              } />

            <Route path="/author/books" element={
              <ProtectedRoute>
                <UserPage />
              </ProtectedRoute>
            } />

            <Route path="/author/edit/:id" element={
              <ProtectedRoute>
                <AuthorEditBookPage />
              </ProtectedRoute>
            } />

            <Route path="/author/books/new" element={
              <ProtectedRoute>
                <PublishPage />
              </ProtectedRoute>
            } />

            <Route path="/admin/books" element={
              <ProtectedRoute role="ROLE_ADMIN">
                <AdminPage />
              </ProtectedRoute>
            } />

            <Route path="/admin/authors" element={
              <ProtectedRoute role="ROLE_ADMIN">
                <AuthorListingPage />
              </ProtectedRoute>
            } />

            <Route path="/admin/edit/:id" element={
              <ProtectedRoute role="ROLE_ADMIN">
                <EditBookPage />
              </ProtectedRoute>
            } />

            <Route path="/admin/edit/author/:id" element={
              <ProtectedRoute role="ROLE_ADMIN">
                <EditAuthorPage />
              </ProtectedRoute>
            } />

            <Route path="/admin/books/new" element={
              <ProtectedRoute role="ROLE_ADMIN">
                <CreateBookPage />
              </ProtectedRoute>
            } />

            <Route path="/admin/authors/new" element={
              <ProtectedRoute role="ROLE_ADMIN">
                  <CreateAuthorPage />
              </ProtectedRoute>
            } />

            <Route path="/admin" element={
              <ProtectedRoute role="ROLE_ADMIN">
                <AdminDashBoard />
              </ProtectedRoute>
            } />

          </Routes>
        </AuthProvider>
      </BrowserRouter>
    </>
  )
}

export default App
