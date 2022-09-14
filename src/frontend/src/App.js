import {
    AuditOutlined,
    BorderlessTableOutlined,
    DiffOutlined,
    EditOutlined,
    FileOutlined,
    FormOutlined,
    GroupOutlined,
    HomeOutlined,
    LoginOutlined,
    LogoutOutlined,
    TeamOutlined,
    UserOutlined,
    ExperimentOutlined,
    GithubOutlined,
    CarryOutOutlined
} from '@ant-design/icons';
import {Layout, Menu, message} from 'antd';
import React, {useEffect, useState} from 'react';
import {Route, Routes, useNavigate} from 'react-router-dom';
import ExamLayout from "./Quiz/ExamLayout";
import Home from "./Home/Home";
import User from "./User/User";
import History from "./History/History";
import Login from "./Login/Login";
import {logout, user} from "./Services/userService";
import {getQuizzes} from "./Clients/quizClient";
import QuizTemplates from "./QuizTemplates/QuizTemplates";
import GradingTables from "./GradingTables/GradingTables";
import QuizSolutionTemplates from "./QuizSolutionTemplates/QuizSolutionTemplates";
import Users from "./Users/Users";
import AssignQuiz from "./AssignQuiz/AssignQuiz";
import ManageQuiz from "./ManageQuiz/ManageQuiz";
import QuizView from "./QuizView/QuizView";
import TaskBlueprints from "./TaskBlueprints/TaskBlueprints";
import AssignTask from "./AssignTask/AssignTask";
import ManageTask from "./ManageTask/ManageTask";
import TaskView from "./TaskView/TaskView";

const {Header, Content, Sider} = Layout;


function App() {

    let activeQuizzes = []
    let historicalQuizzes = []

    function createChildrenForQuizzes(quizzes, text) {
        let table = []
        for (let i = 0; i < quizzes.length; i++) {
            let key_v = text.toLowerCase() + "/" + quizzes[i].testId;
            let label_v = text + " " + i
            table.push(
                {key: key_v, label: label_v}
            )
        }
        return table
    }

    const [state, setState] = useState({
        testId: 1,
        activeItems: [
            {key: "/test/1", label: 'Test 1'},
            {key: "/test/2", label: 'Test 2'},
            {key: "/test/3", label: 'Test 3'},
            {key: "/test/4", label: 'Test 4'},
        ],
        historicalItems: [
            {key: "/history/1", label: 'History 1'},
            {key: "/history/2", label: 'History 2'},
            {key: "/history/3", label: 'History 3'},
            {key: "/history/4", label: 'History 4'},
        ],
    });

    const subMenuQuizzesForTheAdmin = [
        {
            key: "/quiz-templates",
            label: 'Quiz Templates',
            icon: React.createElement(FormOutlined),
        },
        {
            key: "/quiz-solution-templates",
            label: 'Quiz Solution Templates',
            icon: React.createElement(DiffOutlined),
        },
        {
            key: "/grading-tables",
            label: 'Grading Tables',
            icon: React.createElement(BorderlessTableOutlined),
        },
        {
            key: "/assign-quizzes",
            label: 'Assign Quizzes',
            icon: React.createElement(FileOutlined),
        },
        {
            key: "/manage-quizzes",
            label: 'Manage Quizzes',
            icon: React.createElement(GroupOutlined),
        }
    ]

    const subMenuTasksForTheAdmin = [
        {
            key: "/task-blueprints",
            label: 'Task Blueprints',
            icon: React.createElement(GithubOutlined),
        },
        {
            key: "/assign-tasks",
            label: 'Assign Tasks',
            icon: React.createElement(CarryOutOutlined),
        },
        {
            key: "/manage-tasks",
            label: 'Manage Tasks',
            icon: React.createElement(ExperimentOutlined),
        }
    ]

    const menuItemsForTheAdmin = [
        {
            key: "/",
            label: 'Home',
            icon: React.createElement(HomeOutlined),
        },
        {
            key: "/user",
            label: 'User',
            icon: React.createElement(UserOutlined),
        },
        {
            key: "/users",
            label: 'Users',
            icon: React.createElement(TeamOutlined),
        },
        {
            key: "/quizzes",
            label: 'Quizzes',
            icon: React.createElement(EditOutlined),
            children: subMenuQuizzesForTheAdmin
        },
        {
            key: "/tasks",
            label: 'Tasks',
            icon: React.createElement(ExperimentOutlined),
            children: subMenuTasksForTheAdmin
        },
        {
            key: "sign-out",
            label: 'Sign-out',
            icon: React.createElement(LogoutOutlined),
            danger: true,
        },
    ]

    const menuItemsForTheLoggedInUser = [
        {
            key: "/",
            label: 'Home',
            icon: React.createElement(HomeOutlined),
        },
        {
            key: "/user",
            label: 'User',
            icon: React.createElement(UserOutlined),
        },
        {
            key: "/quizzes-view",
            label: 'Quizzes View',
            icon: React.createElement(AuditOutlined),
        },
        {
            key: "/tasks-view",
            label: 'Tasks View',
            icon: React.createElement(ExperimentOutlined),
        },
        {
            key: "/quizzes",
            label: 'Quizzes',
            icon: React.createElement(EditOutlined),
            children: state.activeItems
        },
        {
            key: "sign-out",
            label: 'Sign-out',
            icon: React.createElement(LogoutOutlined),
            danger: true,
        },
    ]

    const menuItemsForAUserWhoIsNotLoggedIn = [
        {
            key: "/",
            label: 'Home',
            icon: React.createElement(HomeOutlined),
        },
        {
            key: "/login",
            label: 'Login',
            icon: React.createElement(LoginOutlined),
        }
    ]

    function selectTheMenu() {
        const currentUser = user();
        if (!currentUser) {
            return [...menuItemsForAUserWhoIsNotLoggedIn];
        } else if (currentUser.includes("ADMIN")) {
            return [...menuItemsForTheAdmin];
        } else
            return [...menuItemsForTheLoggedInUser];
    }

    const menuItems = selectTheMenu()

    const navigate = useNavigate();

    useEffect(() => {
        // console.log("component is mounted...");
    }, []);

    const fetchQuizzes = () =>
        getQuizzes(100, 0)
            .then(res => res.json())
            .then(data => {
                activeQuizzes = data.quizzes.filter(quiz => quiz.status == 'ASSIGNED')
                historicalQuizzes = data.quizzes.filter(quiz => quiz.status != 'ASSIGNED')
                setState({
                    ...state,
                    activeItems: createChildrenForQuizzes(activeQuizzes, "Quiz"),
                    historicalItems: createChildrenForQuizzes(historicalQuizzes, "History"),
                })
            })
            .catch(() => {
                message.error('there was a problem fetching quizzes');
            })
            .finally(() => {

            });

    useEffect(() => {
        fetchQuizzes();
    }, [state.testId]);

    function research(e) {
        let targetContent = e.target.textContent;

        let lastIndexOfSlash = window.location.href.lastIndexOf("/")

        let id = 0
        let activeQuiz = state.activeItems.filter(item => item.label == targetContent).at(0)
        let activeHistory = state.historicalItems.filter(item => item.label == targetContent).at(0)
        if (activeQuiz) {
            lastIndexOfSlash = activeQuiz.key.lastIndexOf("/")
            id = activeQuiz.key.substring(lastIndexOfSlash + 1)
        }
        if (activeHistory) {
            lastIndexOfSlash = activeHistory.key.lastIndexOf("/")
            id = activeHistory.key.substring(lastIndexOfSlash + 1)
        }
        setState({...state, testId: id});
    }

    function privateRoute(privateComponent, defaultComponent) {
        if (user())
            return privateComponent
        return defaultComponent
    }

    return (
        <Layout>
            <Header className="header">
                <div className="logo"/>
            </Header>
            <Layout>
                <Sider width={200} className="site-layout-background" onClickCapture={research}>
                    <Menu
                        onClick={({key}) => {
                            if (key === "sign-out") {
                                logout();
                                navigate("/");
                                window.location.reload()
                            } else {
                                navigate(key);
                            }
                        }}
                        mode="inline"
                        defaultSelectedKeys={['1']}
                        defaultOpenKeys={['sub1']}
                        style={{
                            height: '100%',
                            borderRight: 0,
                        }}
                        items={menuItems}
                    />
                </Sider>
                <Layout
                    style={{
                        padding: '0 24px 24px',
                    }}
                >
                    <Content
                        className="site-layout-background"
                        style={{
                            padding: 24,
                            margin: 0,
                            minHeight: 280,
                        }}
                    >
                        <Routes>
                            <Route path="/" element={<Home/>}></Route>
                            <Route path="/login" element={<Login/>}></Route>
                            <Route path="/user" element={privateRoute(<User/>, <Login/>)}></Route>
                            <Route path="/users" element={privateRoute(<Users/>, <Login/>)}></Route>
                            <Route path="/quiz-templates" element={privateRoute(<QuizTemplates/>, <Login/>)}></Route>
                            <Route path="/quiz-solution-templates" element={privateRoute(<QuizSolutionTemplates/>, <Login/>)}></Route>
                            <Route path="/grading-tables" element={privateRoute(<GradingTables/>, <Login/>)}></Route>
                            <Route path="/assign-quizzes" element={privateRoute(<AssignQuiz/>, <Login/>)}></Route>
                            <Route path="/manage-quizzes" element={privateRoute(<ManageQuiz/>, <Login/>)}></Route>
                            <Route path="/manage-tasks" element={privateRoute(<ManageTask/>, <Login/>)}></Route>
                            <Route path="/task-blueprints" element={privateRoute(<TaskBlueprints/>, <Login/>)}></Route>
                            <Route path="/assign-tasks" element={privateRoute(<AssignTask/>, <Login/>)}></Route>
                            <Route path="/tasks-view/*" element={privateRoute(<TaskView/>,
                                <Login/>)}></Route>
                            <Route path="/quizzes-view/*" element={privateRoute(<QuizView testId={state.testId}></QuizView>,
                                <Login/>)}></Route>
                            <Route path="/quiz/*" element={privateRoute(<ExamLayout testId={state.testId}></ExamLayout>,
                                <Login/>)}></Route>

                        </Routes>

                    </Content>
                </Layout>
            </Layout>
        </Layout>
    );
}

export default App;
