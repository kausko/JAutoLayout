/**
 * This file was generated from canopyVFL.peg
 * See https://canopy.jcoglan.com/ for documentation
 */

package org.JAutoLayout.Parser.canopy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

abstract class Grammar {
    static TreeNode FAILURE = new TreeNode();

    int inputSize, offset, failure;
    String input;
    List<String[]> expected;
    Map<Label, Map<Integer, CacheRecord>> cache;
    Actions actions;

    private static Pattern REGEX_1 = Pattern.compile("\\A[a-zA-Z_]");
    private static Pattern REGEX_2 = Pattern.compile("\\A[a-zA-Z0-9_]");
    private static Pattern REGEX_3 = Pattern.compile("\\A[a-zA-Z_]");
    private static Pattern REGEX_4 = Pattern.compile("\\A[a-zA-Z0-9_]");
    private static Pattern REGEX_5 = Pattern.compile("\\A[+]");
    private static Pattern REGEX_6 = Pattern.compile("\\A[0-9]");
    private static Pattern REGEX_7 = Pattern.compile("\\A[0-9]");
    private static Pattern REGEX_8 = Pattern.compile("\\A[-+]");
    private static Pattern REGEX_9 = Pattern.compile("\\A[0-9]");
    private static Pattern REGEX_10 = Pattern.compile("\\A[0-9]");

    TreeNode _read_visualFormatString() {
        TreeNode address0 = FAILURE;
        int index0 = offset;
        Map<Integer, CacheRecord> rule = cache.get(Label.visualFormatString);
        if (rule == null) {
            rule = new HashMap<Integer, CacheRecord>();
            cache.put(Label.visualFormatString, rule);
        }
        if (rule.containsKey(offset)) {
            address0 = rule.get(offset).node;
            offset = rule.get(offset).tail;
        } else {
            int index1 = offset;
            List<TreeNode> elements0 = new ArrayList<TreeNode>(5);
            TreeNode address1 = FAILURE;
            int index2 = offset;
            int index3 = offset;
            List<TreeNode> elements1 = new ArrayList<TreeNode>(2);
            TreeNode address2 = FAILURE;
            address2 = _read_orientation();
            if (address2 != FAILURE) {
                elements1.add(0, address2);
                TreeNode address3 = FAILURE;
                String chunk0 = null;
                int max0 = offset + 1;
                if (max0 <= inputSize) {
                    chunk0 = input.substring(offset, max0);
                }
                if (chunk0 != null && chunk0.equals(":")) {
                    address3 = new TreeNode(input.substring(offset, offset + 1), offset, new ArrayList<TreeNode>());
                    offset = offset + 1;
                } else {
                    address3 = FAILURE;
                    if (offset > failure) {
                        failure = offset;
                        expected = new ArrayList<String[]>();
                    }
                    if (offset == failure) {
                        expected.add(new String[] { "VFL::visualFormatString", "\":\"" });
                    }
                }
                if (address3 != FAILURE) {
                    elements1.add(1, address3);
                } else {
                    elements1 = null;
                    offset = index3;
                }
            } else {
                elements1 = null;
                offset = index3;
            }
            if (elements1 == null) {
                address1 = FAILURE;
            } else {
                address1 = new TreeNode2(input.substring(index3, offset), index3, elements1);
                offset = offset;
            }
            if (address1 == FAILURE) {
                address1 = new TreeNode(input.substring(index2, index2), index2, new ArrayList<TreeNode>());
                offset = index2;
            }
            if (address1 != FAILURE) {
                elements0.add(0, address1);
                TreeNode address4 = FAILURE;
                int index4 = offset;
                int index5 = offset;
                List<TreeNode> elements2 = new ArrayList<TreeNode>(2);
                TreeNode address5 = FAILURE;
                address5 = _read_superview();
                if (address5 != FAILURE) {
                    elements2.add(0, address5);
                    TreeNode address6 = FAILURE;
                    address6 = _read_connection();
                    if (address6 != FAILURE) {
                        elements2.add(1, address6);
                    } else {
                        elements2 = null;
                        offset = index5;
                    }
                } else {
                    elements2 = null;
                    offset = index5;
                }
                if (elements2 == null) {
                    address4 = FAILURE;
                } else {
                    address4 = new TreeNode3(input.substring(index5, offset), index5, elements2);
                    offset = offset;
                }
                if (address4 == FAILURE) {
                    address4 = new TreeNode(input.substring(index4, index4), index4, new ArrayList<TreeNode>());
                    offset = index4;
                }
                if (address4 != FAILURE) {
                    elements0.add(1, address4);
                    TreeNode address7 = FAILURE;
                    address7 = _read_view();
                    if (address7 != FAILURE) {
                        elements0.add(2, address7);
                        TreeNode address8 = FAILURE;
                        int index6 = offset;
                        List<TreeNode> elements3 = new ArrayList<TreeNode>();
                        TreeNode address9 = null;
                        while (true) {
                            int index7 = offset;
                            List<TreeNode> elements4 = new ArrayList<TreeNode>(2);
                            TreeNode address10 = FAILURE;
                            address10 = _read_connection();
                            if (address10 != FAILURE) {
                                elements4.add(0, address10);
                                TreeNode address11 = FAILURE;
                                address11 = _read_view();
                                if (address11 != FAILURE) {
                                    elements4.add(1, address11);
                                } else {
                                    elements4 = null;
                                    offset = index7;
                                }
                            } else {
                                elements4 = null;
                                offset = index7;
                            }
                            if (elements4 == null) {
                                address9 = FAILURE;
                            } else {
                                address9 = new TreeNode4(input.substring(index7, offset), index7, elements4);
                                offset = offset;
                            }
                            if (address9 != FAILURE) {
                                elements3.add(address9);
                            } else {
                                break;
                            }
                        }
                        if (elements3.size() >= 0) {
                            address8 = new TreeNode(input.substring(index6, offset), index6, elements3);
                            offset = offset;
                        } else {
                            address8 = FAILURE;
                        }
                        if (address8 != FAILURE) {
                            elements0.add(3, address8);
                            TreeNode address12 = FAILURE;
                            int index8 = offset;
                            int index9 = offset;
                            List<TreeNode> elements5 = new ArrayList<TreeNode>(2);
                            TreeNode address13 = FAILURE;
                            address13 = _read_connection();
                            if (address13 != FAILURE) {
                                elements5.add(0, address13);
                                TreeNode address14 = FAILURE;
                                address14 = _read_superview();
                                if (address14 != FAILURE) {
                                    elements5.add(1, address14);
                                } else {
                                    elements5 = null;
                                    offset = index9;
                                }
                            } else {
                                elements5 = null;
                                offset = index9;
                            }
                            if (elements5 == null) {
                                address12 = FAILURE;
                            } else {
                                address12 = new TreeNode5(input.substring(index9, offset), index9, elements5);
                                offset = offset;
                            }
                            if (address12 == FAILURE) {
                                address12 = new TreeNode(input.substring(index8, index8), index8, new ArrayList<TreeNode>());
                                offset = index8;
                            }
                            if (address12 != FAILURE) {
                                elements0.add(4, address12);
                            } else {
                                elements0 = null;
                                offset = index1;
                            }
                        } else {
                            elements0 = null;
                            offset = index1;
                        }
                    } else {
                        elements0 = null;
                        offset = index1;
                    }
                } else {
                    elements0 = null;
                    offset = index1;
                }
            } else {
                elements0 = null;
                offset = index1;
            }
            if (elements0 == null) {
                address0 = FAILURE;
            } else {
                address0 = new TreeNode1(input.substring(index1, offset), index1, elements0);
                offset = offset;
            }
            rule.put(index0, new CacheRecord(address0, offset));
        }
        return address0;
    }

    TreeNode _read_orientation() {
        TreeNode address0 = FAILURE;
        int index0 = offset;
        Map<Integer, CacheRecord> rule = cache.get(Label.orientation);
        if (rule == null) {
            rule = new HashMap<Integer, CacheRecord>();
            cache.put(Label.orientation, rule);
        }
        if (rule.containsKey(offset)) {
            address0 = rule.get(offset).node;
            offset = rule.get(offset).tail;
        } else {
            int index1 = offset;
            String chunk0 = null;
            int max0 = offset + 1;
            if (max0 <= inputSize) {
                chunk0 = input.substring(offset, max0);
            }
            if (chunk0 != null && chunk0.equals("H")) {
                address0 = new TreeNode(input.substring(offset, offset + 1), offset, new ArrayList<TreeNode>());
                offset = offset + 1;
            } else {
                address0 = FAILURE;
                if (offset > failure) {
                    failure = offset;
                    expected = new ArrayList<String[]>();
                }
                if (offset == failure) {
                    expected.add(new String[] { "VFL::orientation", "\"H\"" });
                }
            }
            if (address0 == FAILURE) {
                offset = index1;
                String chunk1 = null;
                int max1 = offset + 1;
                if (max1 <= inputSize) {
                    chunk1 = input.substring(offset, max1);
                }
                if (chunk1 != null && chunk1.equals("V")) {
                    address0 = new TreeNode(input.substring(offset, offset + 1), offset, new ArrayList<TreeNode>());
                    offset = offset + 1;
                } else {
                    address0 = FAILURE;
                    if (offset > failure) {
                        failure = offset;
                        expected = new ArrayList<String[]>();
                    }
                    if (offset == failure) {
                        expected.add(new String[] { "VFL::orientation", "\"V\"" });
                    }
                }
                if (address0 == FAILURE) {
                    offset = index1;
                }
            }
            rule.put(index0, new CacheRecord(address0, offset));
        }
        return address0;
    }

    TreeNode _read_superview() {
        TreeNode address0 = FAILURE;
        int index0 = offset;
        Map<Integer, CacheRecord> rule = cache.get(Label.superview);
        if (rule == null) {
            rule = new HashMap<Integer, CacheRecord>();
            cache.put(Label.superview, rule);
        }
        if (rule.containsKey(offset)) {
            address0 = rule.get(offset).node;
            offset = rule.get(offset).tail;
        } else {
            String chunk0 = null;
            int max0 = offset + 1;
            if (max0 <= inputSize) {
                chunk0 = input.substring(offset, max0);
            }
            if (chunk0 != null && chunk0.equals("|")) {
                address0 = new TreeNode(input.substring(offset, offset + 1), offset, new ArrayList<TreeNode>());
                offset = offset + 1;
            } else {
                address0 = FAILURE;
                if (offset > failure) {
                    failure = offset;
                    expected = new ArrayList<String[]>();
                }
                if (offset == failure) {
                    expected.add(new String[] { "VFL::superview", "\"|\"" });
                }
            }
            rule.put(index0, new CacheRecord(address0, offset));
        }
        return address0;
    }

    TreeNode _read_view() {
        TreeNode address0 = FAILURE;
        int index0 = offset;
        Map<Integer, CacheRecord> rule = cache.get(Label.view);
        if (rule == null) {
            rule = new HashMap<Integer, CacheRecord>();
            cache.put(Label.view, rule);
        }
        if (rule.containsKey(offset)) {
            address0 = rule.get(offset).node;
            offset = rule.get(offset).tail;
        } else {
            int index1 = offset;
            List<TreeNode> elements0 = new ArrayList<TreeNode>(4);
            TreeNode address1 = FAILURE;
            String chunk0 = null;
            int max0 = offset + 1;
            if (max0 <= inputSize) {
                chunk0 = input.substring(offset, max0);
            }
            if (chunk0 != null && chunk0.equals("[")) {
                address1 = new TreeNode(input.substring(offset, offset + 1), offset, new ArrayList<TreeNode>());
                offset = offset + 1;
            } else {
                address1 = FAILURE;
                if (offset > failure) {
                    failure = offset;
                    expected = new ArrayList<String[]>();
                }
                if (offset == failure) {
                    expected.add(new String[] { "VFL::view", "\"[\"" });
                }
            }
            if (address1 != FAILURE) {
                elements0.add(0, address1);
                TreeNode address2 = FAILURE;
                address2 = _read_viewName();
                if (address2 != FAILURE) {
                    elements0.add(1, address2);
                    TreeNode address3 = FAILURE;
                    int index2 = offset;
                    address3 = _read_predicateListWithParens();
                    if (address3 == FAILURE) {
                        address3 = new TreeNode(input.substring(index2, index2), index2, new ArrayList<TreeNode>());
                        offset = index2;
                    }
                    if (address3 != FAILURE) {
                        elements0.add(2, address3);
                        TreeNode address4 = FAILURE;
                        String chunk1 = null;
                        int max1 = offset + 1;
                        if (max1 <= inputSize) {
                            chunk1 = input.substring(offset, max1);
                        }
                        if (chunk1 != null && chunk1.equals("]")) {
                            address4 = new TreeNode(input.substring(offset, offset + 1), offset, new ArrayList<TreeNode>());
                            offset = offset + 1;
                        } else {
                            address4 = FAILURE;
                            if (offset > failure) {
                                failure = offset;
                                expected = new ArrayList<String[]>();
                            }
                            if (offset == failure) {
                                expected.add(new String[] { "VFL::view", "\"]\"" });
                            }
                        }
                        if (address4 != FAILURE) {
                            elements0.add(3, address4);
                        } else {
                            elements0 = null;
                            offset = index1;
                        }
                    } else {
                        elements0 = null;
                        offset = index1;
                    }
                } else {
                    elements0 = null;
                    offset = index1;
                }
            } else {
                elements0 = null;
                offset = index1;
            }
            if (elements0 == null) {
                address0 = FAILURE;
            } else {
                address0 = new TreeNode6(input.substring(index1, offset), index1, elements0);
                offset = offset;
            }
            rule.put(index0, new CacheRecord(address0, offset));
        }
        return address0;
    }

    TreeNode _read_connection() {
        TreeNode address0 = FAILURE;
        int index0 = offset;
        Map<Integer, CacheRecord> rule = cache.get(Label.connection);
        if (rule == null) {
            rule = new HashMap<Integer, CacheRecord>();
            cache.put(Label.connection, rule);
        }
        if (rule.containsKey(offset)) {
            address0 = rule.get(offset).node;
            offset = rule.get(offset).tail;
        } else {
            int index1 = offset;
            int index2 = offset;
            List<TreeNode> elements0 = new ArrayList<TreeNode>(3);
            TreeNode address1 = FAILURE;
            String chunk0 = null;
            int max0 = offset + 1;
            if (max0 <= inputSize) {
                chunk0 = input.substring(offset, max0);
            }
            if (chunk0 != null && chunk0.equals("-")) {
                address1 = new TreeNode(input.substring(offset, offset + 1), offset, new ArrayList<TreeNode>());
                offset = offset + 1;
            } else {
                address1 = FAILURE;
                if (offset > failure) {
                    failure = offset;
                    expected = new ArrayList<String[]>();
                }
                if (offset == failure) {
                    expected.add(new String[] { "VFL::connection", "\"-\"" });
                }
            }
            if (address1 != FAILURE) {
                elements0.add(0, address1);
                TreeNode address2 = FAILURE;
                address2 = _read_predicateList();
                if (address2 != FAILURE) {
                    elements0.add(1, address2);
                    TreeNode address3 = FAILURE;
                    String chunk1 = null;
                    int max1 = offset + 1;
                    if (max1 <= inputSize) {
                        chunk1 = input.substring(offset, max1);
                    }
                    if (chunk1 != null && chunk1.equals("-")) {
                        address3 = new TreeNode(input.substring(offset, offset + 1), offset, new ArrayList<TreeNode>());
                        offset = offset + 1;
                    } else {
                        address3 = FAILURE;
                        if (offset > failure) {
                            failure = offset;
                            expected = new ArrayList<String[]>();
                        }
                        if (offset == failure) {
                            expected.add(new String[] { "VFL::connection", "\"-\"" });
                        }
                    }
                    if (address3 != FAILURE) {
                        elements0.add(2, address3);
                    } else {
                        elements0 = null;
                        offset = index2;
                    }
                } else {
                    elements0 = null;
                    offset = index2;
                }
            } else {
                elements0 = null;
                offset = index2;
            }
            if (elements0 == null) {
                address0 = FAILURE;
            } else {
                address0 = new TreeNode7(input.substring(index2, offset), index2, elements0);
                offset = offset;
            }
            if (address0 == FAILURE) {
                offset = index1;
                String chunk2 = null;
                int max2 = offset + 1;
                if (max2 <= inputSize) {
                    chunk2 = input.substring(offset, max2);
                }
                if (chunk2 != null && chunk2.equals("-")) {
                    address0 = new TreeNode(input.substring(offset, offset + 1), offset, new ArrayList<TreeNode>());
                    offset = offset + 1;
                } else {
                    address0 = FAILURE;
                    if (offset > failure) {
                        failure = offset;
                        expected = new ArrayList<String[]>();
                    }
                    if (offset == failure) {
                        expected.add(new String[] { "VFL::connection", "\"-\"" });
                    }
                }
                if (address0 == FAILURE) {
                    offset = index1;
                    String chunk3 = null;
                    int max3 = offset + 0;
                    if (max3 <= inputSize) {
                        chunk3 = input.substring(offset, max3);
                    }
                    if (chunk3 != null && chunk3.equals("")) {
                        address0 = new TreeNode(input.substring(offset, offset + 0), offset, new ArrayList<TreeNode>());
                        offset = offset + 0;
                    } else {
                        address0 = FAILURE;
                        if (offset > failure) {
                            failure = offset;
                            expected = new ArrayList<String[]>();
                        }
                        if (offset == failure) {
                            expected.add(new String[] { "VFL::connection", "\"\"" });
                        }
                    }
                    if (address0 == FAILURE) {
                        offset = index1;
                    }
                }
            }
            rule.put(index0, new CacheRecord(address0, offset));
        }
        return address0;
    }

    TreeNode _read_predicateList() {
        TreeNode address0 = FAILURE;
        int index0 = offset;
        Map<Integer, CacheRecord> rule = cache.get(Label.predicateList);
        if (rule == null) {
            rule = new HashMap<Integer, CacheRecord>();
            cache.put(Label.predicateList, rule);
        }
        if (rule.containsKey(offset)) {
            address0 = rule.get(offset).node;
            offset = rule.get(offset).tail;
        } else {
            int index1 = offset;
            address0 = _read_simplePredicate();
            if (address0 == FAILURE) {
                offset = index1;
                address0 = _read_predicateListWithParens();
                if (address0 == FAILURE) {
                    offset = index1;
                }
            }
            rule.put(index0, new CacheRecord(address0, offset));
        }
        return address0;
    }

    TreeNode _read_simplePredicate() {
        TreeNode address0 = FAILURE;
        int index0 = offset;
        Map<Integer, CacheRecord> rule = cache.get(Label.simplePredicate);
        if (rule == null) {
            rule = new HashMap<Integer, CacheRecord>();
            cache.put(Label.simplePredicate, rule);
        }
        if (rule.containsKey(offset)) {
            address0 = rule.get(offset).node;
            offset = rule.get(offset).tail;
        } else {
            int index1 = offset;
            address0 = _read_metricName();
            if (address0 == FAILURE) {
                offset = index1;
                address0 = _read_positiveNumber();
                if (address0 == FAILURE) {
                    offset = index1;
                }
            }
            rule.put(index0, new CacheRecord(address0, offset));
        }
        return address0;
    }

    TreeNode _read_predicateListWithParens() {
        TreeNode address0 = FAILURE;
        int index0 = offset;
        Map<Integer, CacheRecord> rule = cache.get(Label.predicateListWithParens);
        if (rule == null) {
            rule = new HashMap<Integer, CacheRecord>();
            cache.put(Label.predicateListWithParens, rule);
        }
        if (rule.containsKey(offset)) {
            address0 = rule.get(offset).node;
            offset = rule.get(offset).tail;
        } else {
            int index1 = offset;
            List<TreeNode> elements0 = new ArrayList<TreeNode>(4);
            TreeNode address1 = FAILURE;
            String chunk0 = null;
            int max0 = offset + 1;
            if (max0 <= inputSize) {
                chunk0 = input.substring(offset, max0);
            }
            if (chunk0 != null && chunk0.equals("(")) {
                address1 = new TreeNode(input.substring(offset, offset + 1), offset, new ArrayList<TreeNode>());
                offset = offset + 1;
            } else {
                address1 = FAILURE;
                if (offset > failure) {
                    failure = offset;
                    expected = new ArrayList<String[]>();
                }
                if (offset == failure) {
                    expected.add(new String[] { "VFL::predicateListWithParens", "\"(\"" });
                }
            }
            if (address1 != FAILURE) {
                elements0.add(0, address1);
                TreeNode address2 = FAILURE;
                address2 = _read_predicate();
                if (address2 != FAILURE) {
                    elements0.add(1, address2);
                    TreeNode address3 = FAILURE;
                    int index2 = offset;
                    List<TreeNode> elements1 = new ArrayList<TreeNode>();
                    TreeNode address4 = null;
                    while (true) {
                        int index3 = offset;
                        List<TreeNode> elements2 = new ArrayList<TreeNode>(2);
                        TreeNode address5 = FAILURE;
                        String chunk1 = null;
                        int max1 = offset + 1;
                        if (max1 <= inputSize) {
                            chunk1 = input.substring(offset, max1);
                        }
                        if (chunk1 != null && chunk1.equals(",")) {
                            address5 = new TreeNode(input.substring(offset, offset + 1), offset, new ArrayList<TreeNode>());
                            offset = offset + 1;
                        } else {
                            address5 = FAILURE;
                            if (offset > failure) {
                                failure = offset;
                                expected = new ArrayList<String[]>();
                            }
                            if (offset == failure) {
                                expected.add(new String[] { "VFL::predicateListWithParens", "\",\"" });
                            }
                        }
                        if (address5 != FAILURE) {
                            elements2.add(0, address5);
                            TreeNode address6 = FAILURE;
                            address6 = _read_predicate();
                            if (address6 != FAILURE) {
                                elements2.add(1, address6);
                            } else {
                                elements2 = null;
                                offset = index3;
                            }
                        } else {
                            elements2 = null;
                            offset = index3;
                        }
                        if (elements2 == null) {
                            address4 = FAILURE;
                        } else {
                            address4 = new TreeNode9(input.substring(index3, offset), index3, elements2);
                            offset = offset;
                        }
                        if (address4 != FAILURE) {
                            elements1.add(address4);
                        } else {
                            break;
                        }
                    }
                    if (elements1.size() >= 0) {
                        address3 = new TreeNode(input.substring(index2, offset), index2, elements1);
                        offset = offset;
                    } else {
                        address3 = FAILURE;
                    }
                    if (address3 != FAILURE) {
                        elements0.add(2, address3);
                        TreeNode address7 = FAILURE;
                        String chunk2 = null;
                        int max2 = offset + 1;
                        if (max2 <= inputSize) {
                            chunk2 = input.substring(offset, max2);
                        }
                        if (chunk2 != null && chunk2.equals(")")) {
                            address7 = new TreeNode(input.substring(offset, offset + 1), offset, new ArrayList<TreeNode>());
                            offset = offset + 1;
                        } else {
                            address7 = FAILURE;
                            if (offset > failure) {
                                failure = offset;
                                expected = new ArrayList<String[]>();
                            }
                            if (offset == failure) {
                                expected.add(new String[] { "VFL::predicateListWithParens", "\")\"" });
                            }
                        }
                        if (address7 != FAILURE) {
                            elements0.add(3, address7);
                        } else {
                            elements0 = null;
                            offset = index1;
                        }
                    } else {
                        elements0 = null;
                        offset = index1;
                    }
                } else {
                    elements0 = null;
                    offset = index1;
                }
            } else {
                elements0 = null;
                offset = index1;
            }
            if (elements0 == null) {
                address0 = FAILURE;
            } else {
                address0 = new TreeNode8(input.substring(index1, offset), index1, elements0);
                offset = offset;
            }
            rule.put(index0, new CacheRecord(address0, offset));
        }
        return address0;
    }

    TreeNode _read_predicate() {
        TreeNode address0 = FAILURE;
        int index0 = offset;
        Map<Integer, CacheRecord> rule = cache.get(Label.predicate);
        if (rule == null) {
            rule = new HashMap<Integer, CacheRecord>();
            cache.put(Label.predicate, rule);
        }
        if (rule.containsKey(offset)) {
            address0 = rule.get(offset).node;
            offset = rule.get(offset).tail;
        } else {
            int index1 = offset;
            List<TreeNode> elements0 = new ArrayList<TreeNode>(3);
            TreeNode address1 = FAILURE;
            int index2 = offset;
            address1 = _read_relation();
            if (address1 == FAILURE) {
                address1 = new TreeNode(input.substring(index2, index2), index2, new ArrayList<TreeNode>());
                offset = index2;
            }
            if (address1 != FAILURE) {
                elements0.add(0, address1);
                TreeNode address2 = FAILURE;
                address2 = _read_objectOfPredicate();
                if (address2 != FAILURE) {
                    elements0.add(1, address2);
                    TreeNode address3 = FAILURE;
                    int index3 = offset;
                    int index4 = offset;
                    List<TreeNode> elements1 = new ArrayList<TreeNode>(2);
                    TreeNode address4 = FAILURE;
                    String chunk0 = null;
                    int max0 = offset + 1;
                    if (max0 <= inputSize) {
                        chunk0 = input.substring(offset, max0);
                    }
                    if (chunk0 != null && chunk0.equals("@")) {
                        address4 = new TreeNode(input.substring(offset, offset + 1), offset, new ArrayList<TreeNode>());
                        offset = offset + 1;
                    } else {
                        address4 = FAILURE;
                        if (offset > failure) {
                            failure = offset;
                            expected = new ArrayList<String[]>();
                        }
                        if (offset == failure) {
                            expected.add(new String[] { "VFL::predicate", "\"@\"" });
                        }
                    }
                    if (address4 != FAILURE) {
                        elements1.add(0, address4);
                        TreeNode address5 = FAILURE;
                        address5 = _read_priority();
                        if (address5 != FAILURE) {
                            elements1.add(1, address5);
                        } else {
                            elements1 = null;
                            offset = index4;
                        }
                    } else {
                        elements1 = null;
                        offset = index4;
                    }
                    if (elements1 == null) {
                        address3 = FAILURE;
                    } else {
                        address3 = new TreeNode11(input.substring(index4, offset), index4, elements1);
                        offset = offset;
                    }
                    if (address3 == FAILURE) {
                        address3 = new TreeNode(input.substring(index3, index3), index3, new ArrayList<TreeNode>());
                        offset = index3;
                    }
                    if (address3 != FAILURE) {
                        elements0.add(2, address3);
                    } else {
                        elements0 = null;
                        offset = index1;
                    }
                } else {
                    elements0 = null;
                    offset = index1;
                }
            } else {
                elements0 = null;
                offset = index1;
            }
            if (elements0 == null) {
                address0 = FAILURE;
            } else {
                address0 = new TreeNode10(input.substring(index1, offset), index1, elements0);
                offset = offset;
            }
            rule.put(index0, new CacheRecord(address0, offset));
        }
        return address0;
    }

    TreeNode _read_relation() {
        TreeNode address0 = FAILURE;
        int index0 = offset;
        Map<Integer, CacheRecord> rule = cache.get(Label.relation);
        if (rule == null) {
            rule = new HashMap<Integer, CacheRecord>();
            cache.put(Label.relation, rule);
        }
        if (rule.containsKey(offset)) {
            address0 = rule.get(offset).node;
            offset = rule.get(offset).tail;
        } else {
            int index1 = offset;
            String chunk0 = null;
            int max0 = offset + 2;
            if (max0 <= inputSize) {
                chunk0 = input.substring(offset, max0);
            }
            if (chunk0 != null && chunk0.equals("==")) {
                address0 = new TreeNode(input.substring(offset, offset + 2), offset, new ArrayList<TreeNode>());
                offset = offset + 2;
            } else {
                address0 = FAILURE;
                if (offset > failure) {
                    failure = offset;
                    expected = new ArrayList<String[]>();
                }
                if (offset == failure) {
                    expected.add(new String[] { "VFL::relation", "\"==\"" });
                }
            }
            if (address0 == FAILURE) {
                offset = index1;
                String chunk1 = null;
                int max1 = offset + 2;
                if (max1 <= inputSize) {
                    chunk1 = input.substring(offset, max1);
                }
                if (chunk1 != null && chunk1.equals("<=")) {
                    address0 = new TreeNode(input.substring(offset, offset + 2), offset, new ArrayList<TreeNode>());
                    offset = offset + 2;
                } else {
                    address0 = FAILURE;
                    if (offset > failure) {
                        failure = offset;
                        expected = new ArrayList<String[]>();
                    }
                    if (offset == failure) {
                        expected.add(new String[] { "VFL::relation", "\"<=\"" });
                    }
                }
                if (address0 == FAILURE) {
                    offset = index1;
                    String chunk2 = null;
                    int max2 = offset + 2;
                    if (max2 <= inputSize) {
                        chunk2 = input.substring(offset, max2);
                    }
                    if (chunk2 != null && chunk2.equals(">=")) {
                        address0 = new TreeNode(input.substring(offset, offset + 2), offset, new ArrayList<TreeNode>());
                        offset = offset + 2;
                    } else {
                        address0 = FAILURE;
                        if (offset > failure) {
                            failure = offset;
                            expected = new ArrayList<String[]>();
                        }
                        if (offset == failure) {
                            expected.add(new String[] { "VFL::relation", "\">=\"" });
                        }
                    }
                    if (address0 == FAILURE) {
                        offset = index1;
                    }
                }
            }
            rule.put(index0, new CacheRecord(address0, offset));
        }
        return address0;
    }

    TreeNode _read_objectOfPredicate() {
        TreeNode address0 = FAILURE;
        int index0 = offset;
        Map<Integer, CacheRecord> rule = cache.get(Label.objectOfPredicate);
        if (rule == null) {
            rule = new HashMap<Integer, CacheRecord>();
            cache.put(Label.objectOfPredicate, rule);
        }
        if (rule.containsKey(offset)) {
            address0 = rule.get(offset).node;
            offset = rule.get(offset).tail;
        } else {
            int index1 = offset;
            address0 = _read_constant();
            if (address0 == FAILURE) {
                offset = index1;
                address0 = _read_viewName();
                if (address0 == FAILURE) {
                    offset = index1;
                }
            }
            rule.put(index0, new CacheRecord(address0, offset));
        }
        return address0;
    }

    TreeNode _read_priority() {
        TreeNode address0 = FAILURE;
        int index0 = offset;
        Map<Integer, CacheRecord> rule = cache.get(Label.priority);
        if (rule == null) {
            rule = new HashMap<Integer, CacheRecord>();
            cache.put(Label.priority, rule);
        }
        if (rule.containsKey(offset)) {
            address0 = rule.get(offset).node;
            offset = rule.get(offset).tail;
        } else {
            int index1 = offset;
            address0 = _read_metricName();
            if (address0 == FAILURE) {
                offset = index1;
                address0 = _read_number();
                if (address0 == FAILURE) {
                    offset = index1;
                }
            }
            rule.put(index0, new CacheRecord(address0, offset));
        }
        return address0;
    }

    TreeNode _read_constant() {
        TreeNode address0 = FAILURE;
        int index0 = offset;
        Map<Integer, CacheRecord> rule = cache.get(Label.constant);
        if (rule == null) {
            rule = new HashMap<Integer, CacheRecord>();
            cache.put(Label.constant, rule);
        }
        if (rule.containsKey(offset)) {
            address0 = rule.get(offset).node;
            offset = rule.get(offset).tail;
        } else {
            int index1 = offset;
            address0 = _read_metricName();
            if (address0 == FAILURE) {
                offset = index1;
                address0 = _read_number();
                if (address0 == FAILURE) {
                    offset = index1;
                }
            }
            rule.put(index0, new CacheRecord(address0, offset));
        }
        return address0;
    }

    TreeNode _read_viewName() {
        TreeNode address0 = FAILURE;
        int index0 = offset;
        Map<Integer, CacheRecord> rule = cache.get(Label.viewName);
        if (rule == null) {
            rule = new HashMap<Integer, CacheRecord>();
            cache.put(Label.viewName, rule);
        }
        if (rule.containsKey(offset)) {
            address0 = rule.get(offset).node;
            offset = rule.get(offset).tail;
        } else {
            int index1 = offset;
            List<TreeNode> elements0 = new ArrayList<TreeNode>(2);
            TreeNode address1 = FAILURE;
            String chunk0 = null;
            int max0 = offset + 1;
            if (max0 <= inputSize) {
                chunk0 = input.substring(offset, max0);
            }
            if (chunk0 != null && REGEX_1.matcher(chunk0).matches()) {
                address1 = new TreeNode(input.substring(offset, offset + 1), offset, new ArrayList<TreeNode>());
                offset = offset + 1;
            } else {
                address1 = FAILURE;
                if (offset > failure) {
                    failure = offset;
                    expected = new ArrayList<String[]>();
                }
                if (offset == failure) {
                    expected.add(new String[] { "VFL::viewName", "[a-zA-Z_]" });
                }
            }
            if (address1 != FAILURE) {
                elements0.add(0, address1);
                TreeNode address2 = FAILURE;
                int index2 = offset;
                List<TreeNode> elements1 = new ArrayList<TreeNode>();
                TreeNode address3 = null;
                while (true) {
                    String chunk1 = null;
                    int max1 = offset + 1;
                    if (max1 <= inputSize) {
                        chunk1 = input.substring(offset, max1);
                    }
                    if (chunk1 != null && REGEX_2.matcher(chunk1).matches()) {
                        address3 = new TreeNode(input.substring(offset, offset + 1), offset, new ArrayList<TreeNode>());
                        offset = offset + 1;
                    } else {
                        address3 = FAILURE;
                        if (offset > failure) {
                            failure = offset;
                            expected = new ArrayList<String[]>();
                        }
                        if (offset == failure) {
                            expected.add(new String[] { "VFL::viewName", "[a-zA-Z0-9_]" });
                        }
                    }
                    if (address3 != FAILURE) {
                        elements1.add(address3);
                    } else {
                        break;
                    }
                }
                if (elements1.size() >= 0) {
                    address2 = new TreeNode(input.substring(index2, offset), index2, elements1);
                    offset = offset;
                } else {
                    address2 = FAILURE;
                }
                if (address2 != FAILURE) {
                    elements0.add(1, address2);
                } else {
                    elements0 = null;
                    offset = index1;
                }
            } else {
                elements0 = null;
                offset = index1;
            }
            if (elements0 == null) {
                address0 = FAILURE;
            } else {
                address0 = new TreeNode(input.substring(index1, offset), index1, elements0);
                offset = offset;
            }
            rule.put(index0, new CacheRecord(address0, offset));
        }
        return address0;
    }

    TreeNode _read_metricName() {
        TreeNode address0 = FAILURE;
        int index0 = offset;
        Map<Integer, CacheRecord> rule = cache.get(Label.metricName);
        if (rule == null) {
            rule = new HashMap<Integer, CacheRecord>();
            cache.put(Label.metricName, rule);
        }
        if (rule.containsKey(offset)) {
            address0 = rule.get(offset).node;
            offset = rule.get(offset).tail;
        } else {
            int index1 = offset;
            List<TreeNode> elements0 = new ArrayList<TreeNode>(2);
            TreeNode address1 = FAILURE;
            String chunk0 = null;
            int max0 = offset + 1;
            if (max0 <= inputSize) {
                chunk0 = input.substring(offset, max0);
            }
            if (chunk0 != null && REGEX_3.matcher(chunk0).matches()) {
                address1 = new TreeNode(input.substring(offset, offset + 1), offset, new ArrayList<TreeNode>());
                offset = offset + 1;
            } else {
                address1 = FAILURE;
                if (offset > failure) {
                    failure = offset;
                    expected = new ArrayList<String[]>();
                }
                if (offset == failure) {
                    expected.add(new String[] { "VFL::metricName", "[a-zA-Z_]" });
                }
            }
            if (address1 != FAILURE) {
                elements0.add(0, address1);
                TreeNode address2 = FAILURE;
                int index2 = offset;
                List<TreeNode> elements1 = new ArrayList<TreeNode>();
                TreeNode address3 = null;
                while (true) {
                    String chunk1 = null;
                    int max1 = offset + 1;
                    if (max1 <= inputSize) {
                        chunk1 = input.substring(offset, max1);
                    }
                    if (chunk1 != null && REGEX_4.matcher(chunk1).matches()) {
                        address3 = new TreeNode(input.substring(offset, offset + 1), offset, new ArrayList<TreeNode>());
                        offset = offset + 1;
                    } else {
                        address3 = FAILURE;
                        if (offset > failure) {
                            failure = offset;
                            expected = new ArrayList<String[]>();
                        }
                        if (offset == failure) {
                            expected.add(new String[] { "VFL::metricName", "[a-zA-Z0-9_]" });
                        }
                    }
                    if (address3 != FAILURE) {
                        elements1.add(address3);
                    } else {
                        break;
                    }
                }
                if (elements1.size() >= 0) {
                    address2 = new TreeNode(input.substring(index2, offset), index2, elements1);
                    offset = offset;
                } else {
                    address2 = FAILURE;
                }
                if (address2 != FAILURE) {
                    elements0.add(1, address2);
                } else {
                    elements0 = null;
                    offset = index1;
                }
            } else {
                elements0 = null;
                offset = index1;
            }
            if (elements0 == null) {
                address0 = FAILURE;
            } else {
                address0 = new TreeNode(input.substring(index1, offset), index1, elements0);
                offset = offset;
            }
            rule.put(index0, new CacheRecord(address0, offset));
        }
        return address0;
    }

    TreeNode _read_positiveNumber() {
        TreeNode address0 = FAILURE;
        int index0 = offset;
        Map<Integer, CacheRecord> rule = cache.get(Label.positiveNumber);
        if (rule == null) {
            rule = new HashMap<Integer, CacheRecord>();
            cache.put(Label.positiveNumber, rule);
        }
        if (rule.containsKey(offset)) {
            address0 = rule.get(offset).node;
            offset = rule.get(offset).tail;
        } else {
            int index1 = offset;
            List<TreeNode> elements0 = new ArrayList<TreeNode>(3);
            TreeNode address1 = FAILURE;
            int index2 = offset;
            String chunk0 = null;
            int max0 = offset + 1;
            if (max0 <= inputSize) {
                chunk0 = input.substring(offset, max0);
            }
            if (chunk0 != null && REGEX_5.matcher(chunk0).matches()) {
                address1 = new TreeNode(input.substring(offset, offset + 1), offset, new ArrayList<TreeNode>());
                offset = offset + 1;
            } else {
                address1 = FAILURE;
                if (offset > failure) {
                    failure = offset;
                    expected = new ArrayList<String[]>();
                }
                if (offset == failure) {
                    expected.add(new String[] { "VFL::positiveNumber", "[+]" });
                }
            }
            if (address1 == FAILURE) {
                address1 = new TreeNode(input.substring(index2, index2), index2, new ArrayList<TreeNode>());
                offset = index2;
            }
            if (address1 != FAILURE) {
                elements0.add(0, address1);
                TreeNode address2 = FAILURE;
                int index3 = offset;
                List<TreeNode> elements1 = new ArrayList<TreeNode>();
                TreeNode address3 = null;
                while (true) {
                    String chunk1 = null;
                    int max1 = offset + 1;
                    if (max1 <= inputSize) {
                        chunk1 = input.substring(offset, max1);
                    }
                    if (chunk1 != null && REGEX_6.matcher(chunk1).matches()) {
                        address3 = new TreeNode(input.substring(offset, offset + 1), offset, new ArrayList<TreeNode>());
                        offset = offset + 1;
                    } else {
                        address3 = FAILURE;
                        if (offset > failure) {
                            failure = offset;
                            expected = new ArrayList<String[]>();
                        }
                        if (offset == failure) {
                            expected.add(new String[] { "VFL::positiveNumber", "[0-9]" });
                        }
                    }
                    if (address3 != FAILURE) {
                        elements1.add(address3);
                    } else {
                        break;
                    }
                }
                if (elements1.size() >= 0) {
                    address2 = new TreeNode(input.substring(index3, offset), index3, elements1);
                    offset = offset;
                } else {
                    address2 = FAILURE;
                }
                if (address2 != FAILURE) {
                    elements0.add(1, address2);
                    TreeNode address4 = FAILURE;
                    int index4 = offset;
                    int index5 = offset;
                    List<TreeNode> elements2 = new ArrayList<TreeNode>(2);
                    TreeNode address5 = FAILURE;
                    String chunk2 = null;
                    int max2 = offset + 1;
                    if (max2 <= inputSize) {
                        chunk2 = input.substring(offset, max2);
                    }
                    if (chunk2 != null && chunk2.equals(".")) {
                        address5 = new TreeNode(input.substring(offset, offset + 1), offset, new ArrayList<TreeNode>());
                        offset = offset + 1;
                    } else {
                        address5 = FAILURE;
                        if (offset > failure) {
                            failure = offset;
                            expected = new ArrayList<String[]>();
                        }
                        if (offset == failure) {
                            expected.add(new String[] { "VFL::positiveNumber", "\".\"" });
                        }
                    }
                    if (address5 != FAILURE) {
                        elements2.add(0, address5);
                        TreeNode address6 = FAILURE;
                        int index6 = offset;
                        List<TreeNode> elements3 = new ArrayList<TreeNode>();
                        TreeNode address7 = null;
                        while (true) {
                            String chunk3 = null;
                            int max3 = offset + 1;
                            if (max3 <= inputSize) {
                                chunk3 = input.substring(offset, max3);
                            }
                            if (chunk3 != null && REGEX_7.matcher(chunk3).matches()) {
                                address7 = new TreeNode(input.substring(offset, offset + 1), offset, new ArrayList<TreeNode>());
                                offset = offset + 1;
                            } else {
                                address7 = FAILURE;
                                if (offset > failure) {
                                    failure = offset;
                                    expected = new ArrayList<String[]>();
                                }
                                if (offset == failure) {
                                    expected.add(new String[] { "VFL::positiveNumber", "[0-9]" });
                                }
                            }
                            if (address7 != FAILURE) {
                                elements3.add(address7);
                            } else {
                                break;
                            }
                        }
                        if (elements3.size() >= 1) {
                            address6 = new TreeNode(input.substring(index6, offset), index6, elements3);
                            offset = offset;
                        } else {
                            address6 = FAILURE;
                        }
                        if (address6 != FAILURE) {
                            elements2.add(1, address6);
                        } else {
                            elements2 = null;
                            offset = index5;
                        }
                    } else {
                        elements2 = null;
                        offset = index5;
                    }
                    if (elements2 == null) {
                        address4 = FAILURE;
                    } else {
                        address4 = new TreeNode(input.substring(index5, offset), index5, elements2);
                        offset = offset;
                    }
                    if (address4 == FAILURE) {
                        address4 = new TreeNode(input.substring(index4, index4), index4, new ArrayList<TreeNode>());
                        offset = index4;
                    }
                    if (address4 != FAILURE) {
                        elements0.add(2, address4);
                    } else {
                        elements0 = null;
                        offset = index1;
                    }
                } else {
                    elements0 = null;
                    offset = index1;
                }
            } else {
                elements0 = null;
                offset = index1;
            }
            if (elements0 == null) {
                address0 = FAILURE;
            } else {
                address0 = new TreeNode(input.substring(index1, offset), index1, elements0);
                offset = offset;
            }
            rule.put(index0, new CacheRecord(address0, offset));
        }
        return address0;
    }

    TreeNode _read_number() {
        TreeNode address0 = FAILURE;
        int index0 = offset;
        Map<Integer, CacheRecord> rule = cache.get(Label.number);
        if (rule == null) {
            rule = new HashMap<Integer, CacheRecord>();
            cache.put(Label.number, rule);
        }
        if (rule.containsKey(offset)) {
            address0 = rule.get(offset).node;
            offset = rule.get(offset).tail;
        } else {
            int index1 = offset;
            List<TreeNode> elements0 = new ArrayList<TreeNode>(3);
            TreeNode address1 = FAILURE;
            int index2 = offset;
            String chunk0 = null;
            int max0 = offset + 1;
            if (max0 <= inputSize) {
                chunk0 = input.substring(offset, max0);
            }
            if (chunk0 != null && REGEX_8.matcher(chunk0).matches()) {
                address1 = new TreeNode(input.substring(offset, offset + 1), offset, new ArrayList<TreeNode>());
                offset = offset + 1;
            } else {
                address1 = FAILURE;
                if (offset > failure) {
                    failure = offset;
                    expected = new ArrayList<String[]>();
                }
                if (offset == failure) {
                    expected.add(new String[] { "VFL::number", "[-+]" });
                }
            }
            if (address1 == FAILURE) {
                address1 = new TreeNode(input.substring(index2, index2), index2, new ArrayList<TreeNode>());
                offset = index2;
            }
            if (address1 != FAILURE) {
                elements0.add(0, address1);
                TreeNode address2 = FAILURE;
                int index3 = offset;
                List<TreeNode> elements1 = new ArrayList<TreeNode>();
                TreeNode address3 = null;
                while (true) {
                    String chunk1 = null;
                    int max1 = offset + 1;
                    if (max1 <= inputSize) {
                        chunk1 = input.substring(offset, max1);
                    }
                    if (chunk1 != null && REGEX_9.matcher(chunk1).matches()) {
                        address3 = new TreeNode(input.substring(offset, offset + 1), offset, new ArrayList<TreeNode>());
                        offset = offset + 1;
                    } else {
                        address3 = FAILURE;
                        if (offset > failure) {
                            failure = offset;
                            expected = new ArrayList<String[]>();
                        }
                        if (offset == failure) {
                            expected.add(new String[] { "VFL::number", "[0-9]" });
                        }
                    }
                    if (address3 != FAILURE) {
                        elements1.add(address3);
                    } else {
                        break;
                    }
                }
                if (elements1.size() >= 0) {
                    address2 = new TreeNode(input.substring(index3, offset), index3, elements1);
                    offset = offset;
                } else {
                    address2 = FAILURE;
                }
                if (address2 != FAILURE) {
                    elements0.add(1, address2);
                    TreeNode address4 = FAILURE;
                    int index4 = offset;
                    int index5 = offset;
                    List<TreeNode> elements2 = new ArrayList<TreeNode>(2);
                    TreeNode address5 = FAILURE;
                    String chunk2 = null;
                    int max2 = offset + 1;
                    if (max2 <= inputSize) {
                        chunk2 = input.substring(offset, max2);
                    }
                    if (chunk2 != null && chunk2.equals(".")) {
                        address5 = new TreeNode(input.substring(offset, offset + 1), offset, new ArrayList<TreeNode>());
                        offset = offset + 1;
                    } else {
                        address5 = FAILURE;
                        if (offset > failure) {
                            failure = offset;
                            expected = new ArrayList<String[]>();
                        }
                        if (offset == failure) {
                            expected.add(new String[] { "VFL::number", "\".\"" });
                        }
                    }
                    if (address5 != FAILURE) {
                        elements2.add(0, address5);
                        TreeNode address6 = FAILURE;
                        int index6 = offset;
                        List<TreeNode> elements3 = new ArrayList<TreeNode>();
                        TreeNode address7 = null;
                        while (true) {
                            String chunk3 = null;
                            int max3 = offset + 1;
                            if (max3 <= inputSize) {
                                chunk3 = input.substring(offset, max3);
                            }
                            if (chunk3 != null && REGEX_10.matcher(chunk3).matches()) {
                                address7 = new TreeNode(input.substring(offset, offset + 1), offset, new ArrayList<TreeNode>());
                                offset = offset + 1;
                            } else {
                                address7 = FAILURE;
                                if (offset > failure) {
                                    failure = offset;
                                    expected = new ArrayList<String[]>();
                                }
                                if (offset == failure) {
                                    expected.add(new String[] { "VFL::number", "[0-9]" });
                                }
                            }
                            if (address7 != FAILURE) {
                                elements3.add(address7);
                            } else {
                                break;
                            }
                        }
                        if (elements3.size() >= 1) {
                            address6 = new TreeNode(input.substring(index6, offset), index6, elements3);
                            offset = offset;
                        } else {
                            address6 = FAILURE;
                        }
                        if (address6 != FAILURE) {
                            elements2.add(1, address6);
                        } else {
                            elements2 = null;
                            offset = index5;
                        }
                    } else {
                        elements2 = null;
                        offset = index5;
                    }
                    if (elements2 == null) {
                        address4 = FAILURE;
                    } else {
                        address4 = new TreeNode(input.substring(index5, offset), index5, elements2);
                        offset = offset;
                    }
                    if (address4 == FAILURE) {
                        address4 = new TreeNode(input.substring(index4, index4), index4, new ArrayList<TreeNode>());
                        offset = index4;
                    }
                    if (address4 != FAILURE) {
                        elements0.add(2, address4);
                    } else {
                        elements0 = null;
                        offset = index1;
                    }
                } else {
                    elements0 = null;
                    offset = index1;
                }
            } else {
                elements0 = null;
                offset = index1;
            }
            if (elements0 == null) {
                address0 = FAILURE;
            } else {
                address0 = new TreeNode(input.substring(index1, offset), index1, elements0);
                offset = offset;
            }
            rule.put(index0, new CacheRecord(address0, offset));
        }
        return address0;
    }
}
