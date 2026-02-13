class Expr a where
    val :: Int -> a
    add :: a -> a -> a
    sub :: a -> a -> a
    mul :: a -> a -> a

newtype PostFix = PostFix { getRPN :: String }

instance Expr PostFix where
    val n = PostFix (show n)
    add x y = PostFix (getRPN x ++ " " ++ getRPN y ++ " +")
    sub x y = PostFix (getRPN x ++ " " ++ getRPN y ++ " -")
    mul x y = PostFix (getRPN x ++ " " ++ getRPN y ++ " *")

newtype Eval = Eval { getVal :: Int }

instance Expr Eval where
    val n = Eval n
    add x y = Eval (getVal x + getVal y)
    sub x y = Eval (getVal x - getVal y)
    mul x y = Eval (getVal x * getVal y)

expr :: Expr a => a
expr = mul (add (val 1) (val 2))
           (sub (val 4) (val 3))

main :: IO ()
main = do
    let rpn  = expr :: PostFix
    let eval = expr :: Eval
    putStrLn "Infix: (1 + 2) * (4 - 3)"
    putStrLn ("RPN:   " ++ getRPN rpn)
    putStrLn ("Eval:  " ++ show (getVal eval))
