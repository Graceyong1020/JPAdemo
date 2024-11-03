package jpabook.japshop.controller;

import jakarta.validation.Valid;
import jpabook.japshop.domain.Address;
import jpabook.japshop.domain.Member;
import jpabook.japshop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) { // model: controller에서 view로 데이터를 넘길 때 사용
        model.addAttribute("memberForm", new MemberForm()); // 화면에서 사용할 수 있는 객체를 생성하여 model에 담아서 view로 넘김
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result) { // BindingResult: 검증 오류 결과를 보관, 검증 오류가 발생해도 실행이 중단되지 않고 계속 진행

        if (result.hasErrors()) {
            return "members/createMemberForm";
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());
        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/";

    }
    // 회원 목록 조회
    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }



}
