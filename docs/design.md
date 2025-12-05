# Customer Manager 機能設計

## 画面一覧
- /customers          : 顧客一覧
- /customers/new      : 顧客登録（GET/POST）
- /customers/{id}/edit : 顧客編集（GET/POST）
- /customers/{id}/delete : 顧客削除（POST）

## エンティティ
Customer
- id: Long
- name: String
- email: String
- phone: String
- rank: String
- status: String
- memo: String
- active: Boolean

## バリデーション
- name : @NotBlank
- email : @Email
- phone : @Pattern（あれば）
