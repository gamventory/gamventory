package com.gamventory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gamventory.entity.Notice;
import com.gamventory.service.NoticeService;

@Controller
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @GetMapping("/notices")
    public String listNotices(Model model, @RequestParam(defaultValue = "0") int page) {
    int pageSize = 10; // 한 페이지에 보여줄 항목 수

    Page<Notice> pageNotices = noticeService.findAll(PageRequest.of(page, pageSize));
    model.addAttribute("notices", pageNotices);

    return "notice/list";
}

    @GetMapping("/notices/{id}")
    public String view(@PathVariable Long id, Model model) {
        Notice notice = noticeService.getNoticeById(id);
        model.addAttribute("notice", notice);
        return "notice/view";
    }

    @GetMapping("/notices/new")
    public String createForm(Model model) {
        model.addAttribute("notice", new Notice());
        return "notice/create";
    }

    @PostMapping("/notices/new")
    public String create(Notice notice) {
        noticeService.saveNotice(notice);
        return "redirect:/notices";
    }

    @GetMapping("/notices/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Notice notice = noticeService.getNoticeById(id);
        model.addAttribute("notice", notice);
        return "notice/edit";
    }

    @PostMapping("/notices/{id}/edit")
    public String edit(@PathVariable Long id, Notice updatedNotice) {
        Notice notice = noticeService.getNoticeById(id);
        if (notice != null) {
            notice.setTitle(updatedNotice.getTitle());
            notice.setContent(updatedNotice.getContent());
            noticeService.saveNotice(notice);
        }
        return "redirect:/notices";
    }

    @GetMapping("/notices/{id}/delete")
    public String delete(@PathVariable Long id) {
        noticeService.deleteNotice(id);
        return "redirect:/notices";
    }
}

