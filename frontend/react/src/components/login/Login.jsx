import {
    Alert,
    AlertIcon,
    Box,
    Button,
    Flex,
    FormLabel,
    Heading,
    Image,
    Input,
    Link,
    Stack,
    Text,
} from '@chakra-ui/react';
import {Form, Formik, useField} from "formik";
import * as Yup from 'yup';
import {useNavigate} from "react-router-dom";
import {useEffect} from "react";
import {errorAlert} from "../../services/AlertToast.js";
import {useAuth} from "../../context/AuthContext.jsx";

const MyTextInput = ({label, ...props}) => {
    // useField() returns [formik.getFieldProps(), formik.getFieldMeta()]
    // which we can spread on <input>. We can use field meta to show an error
    // message if the field is invalid and it has been touched (i.e. visited)
    const [field, meta] = useField(props);
    return (
        <Box>
            <FormLabel htmlFor={props.id || props.name}>{label}</FormLabel>
            <Input className="text-input" {...field} {...props} />
            {meta.touched && meta.error ? (
                <Alert className="error" status={"error"} mt={2}>
                    <AlertIcon/>
                    {meta.error}
                </Alert>
            ) : null}
        </Box>
    );
};

const LoginForm = () => {
    const {login} = useAuth();
    const navigate = useNavigate();

    return (
        <Formik
            validateOnMount={true}
            validationSchema={
                Yup.object({
                    userName: Yup.string()
                        .required("User Name is required"),
                    password: Yup.string()
                        .min(4, "Password must be more than 4 characters")
                        .required("Password is required")
                })
            }
            initialValues={{userName: '', password: ''}}
            onSubmit={(values, {setSubmitting}) => {
                setSubmitting(true);
                login(values).then(res => {
                    navigate("/dashboard")
                    console.log("Successfully logged in");
                }).catch(err => {
                    errorAlert(
                        err.code,
                        err.response.data.message
                    )
                }).finally(() => {
                    setSubmitting(false);
                })
            }}>

            {({isValid, isSubmitting}) => (
                <Form>
                    <Stack mt={15} spacing={15}>
                        <MyTextInput
                            label={"User Name"}
                            name={"userName"}
                            type={"text"}
                            placeholder={"Jane@codeNaren.com"}
                        />
                        <MyTextInput
                            label={"Password"}
                            name={"password"}
                            type={"password"}
                            placeholder={"Type your password"}
                        />

                        <Button
                            type={"submit"}
                            disabled={!isValid || isSubmitting}>
                            Login
                        </Button>
                    </Stack>
                </Form>
            )}

        </Formik>
    )
}

const Login = () => {

    const {customer} = useAuth();
    const navigate = useNavigate();

    useEffect(() => {
        if (customer) {
            navigate("/dashboard")
        }
    })

    return (
        <Stack minH={'100vh'} direction={{base: 'column', md: 'row'}}>
            <Flex p={5} flex={1} align={'center'} justify={'center'}>
                <Stack spacing={3} w={'full'} maxW={'sm'}>
                    <Image
                        src={"src/assets/Codenaren.png"}
                        boxSize={"150px"}
                        minW={"420px"}
                        alt={"CodeNaren Logo"}
                        alignSelf={"center"}
                    />
                    <Heading fontSize={'2xl'} mb={15}
                             alignSelf={"center"}
                    >Sign in to your account</Heading>
                    <LoginForm/>
                    <Link color={"blue.500"} href={"/signup"} textAlign={"end"}>
                        Dont have an account?.. SignUp now.
                    </Link>
                </Stack>
            </Flex>
            <Flex
                flex={1}
                p={10}
                flexDirection={"column"}
                alignItems={"center"}
                justifyContent={"center"}
                bgGradient={{sm: 'linear(to-r, blue.600, purple.600)'}}
            >
                <Text fontSize={"6xl"} color={'white'} fontWeight={"bold"} mb={5}>
                    <Link target={"_blank"} href={"https://amigoscode.com/courses"}>
                        Enroll Now
                    </Link>
                </Text>
                <Image
                    alt={'Login Image'}
                    objectFit={'scale-down'}
                    src={
                        'https://user-images.githubusercontent.com/40702606/215539167-d7006790-b880-4929-83fb-c43fa74f429e.png'
                    }
                />
            </Flex>
        </Stack>
    );
}

export default Login;