import {useAuth} from "../../context/AuthContext.jsx";
import {useNavigate} from "react-router-dom";
import {useEffect} from "react";
import {Flex, Heading, Image, Link, Stack, Text} from "@chakra-ui/react";
import CreateCustomerForm from "../CreateCustomerForm.jsx";

const Register = () => {
    const {customer, setCustomerFromToken} = useAuth();
    const navigate = useNavigate();

    useEffect(() => {
        if (customer) {
            navigate("/dashboard")
        }
    })

    return (
        <>
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
                        >Register for an account</Heading>
                        <CreateCustomerForm onSucess={(token)=>{
                            localStorage.setItem("AccessToken", token);
                            setCustomerFromToken();
                            navigate("/dashboard");
                        }}/>
                        <Link color={"blue.500"} href={"/"} textAlign={"end"}>
                             Have an account?.. Login now.
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
        </>
    )
}

export default Register;