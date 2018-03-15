package com.wayeasoft.springmvc.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test/api")
public class ApiTestController
{

        // 错误常量要在controller中定义常量，不能直接在构造错误的时候写数字
        public final static int INPUT_EVEN_ERROR = 4001;

        public final static int INPUT_ODD_ERROR = 4002;

        @Autowired
        private ApiTestService apiTestService;

        @RequestMapping("/test")
        public ResultBean<Integer> test(int inputInteger)
        {
                // controller负责判断输入是否合法
                // 这里模拟判断逻辑，逻辑要求是，必须输入0，如果输入不是0，为奇数的时候会报不能为奇数的异常，为偶数的时候会报不能为偶数的异常。这两种异常都是客户端异常
                // 假设这里的逻辑写错了，漏判断了输入为1的情况。这个错误的数据传到service中后，service会做判断，并抛出异常。这种异常是服务端异常。
                if (inputInteger != 0 && inputInteger != 1
                                && inputInteger % 2 == 0)
                {
                        throw new InputEvenException().set("input integer",
                                        inputInteger);
                }

                if (inputInteger != 0 && inputInteger != 1
                                && inputInteger % 2 != 0)
                {
                        throw new InputOddException().set("input integer",
                                        inputInteger);
                }

                return new ResultBean<>(
                                apiTestService.getTestInteger(inputInteger));
        }

}
