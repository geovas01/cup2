package edu.tum.cup2.spec;


import edu.tum.cup2.grammar.NonTerminal;
import edu.tum.cup2.grammar.SpecialTerminals;
import edu.tum.cup2.grammar.Terminal;
import edu.tum.cup2.spec.CUPSpecification;
import edu.tum.cup2.semantics.SymbolValue;
import edu.tum.cup2.semantics.Action;
import static edu.tum.cup2.grammar.SpecialTerminals.Epsilon; 
import static edu.tum.cup2.spec.MeineKlasse.Terminals.*;
import static edu.tum.cup2.spec.MeineKlasse.NonTerminals.*;

public class MeineKlasse extends CUPSpecification {
    
public enum Terminals implements Terminal {
PACKAGE,STAR,LBRACK,EDGE,LBRACE,ATTRIBUTE,UPDATE,EQUALS,NOT,ATOM,AND,LPAREN,ACTIONS,RPAREN,METHODS,SCOPE,COMMA,IS,ELEMENT,ACCESS,IDENT,WORKFLOW,FORM,DOT,WITH,RBRACK,SYNTHESIZED,NODE,RBRACE,SEMICOLON,EXEC,MULT,OF,INHERITED,LINE,LAYOUT,WRITE,DOUBLEARROW,CHANGE,STARTACTIONCODE,NOACCESS,COLON,ENUM,PREDICATES,SINGLE,READ,LHS,OR,SUBTREES,USER,ARROW,BAR;
}

public enum NonTerminals implements NonTerminal {
selector,methods,accessDefList,predicate,predicateList,workflowDefList,packageOpt,production,accessOp,actionOp,userType,alternativeList,attribDecl,accessRuleList,formelOp,rhs,argument,actionRuleList,transmitVisitorOpt,filterOpt,itemListOpt,alternative,predicateDef,formelList,workflowDef,accessDef,precondition,identList,formelRest,transitionList,accessEntry,identListOpt,lines,layouts,preActionResult,argumentOpt,userTypeListOpt,update,packageIdent,scopeOpt,predicateDefList,actionDefList,actionDef,withOpt,formel,actionResult,layout,selectorOpt,accessRule,updates,actionRule,accessEntryList,transition,argumentList,typeOpt,nameOpt,layouttype,attribList,itemList,item,actionResultList,specification,specifier,method,actionAlt,productionList,userTypeArguments,formelListOpt,
userTypeList;
}

public MeineKlasse() {
grammar(

prod(
  NonTerminals.specification,
  rhs(packageOpt, productionList, attribList, predicateDefList, methods, updates, layouts, accessDefList, actionDefList, workflowDefList, userTypeListOpt 
/*    new Action() {
      public Specification a(String p, ProductionList l, AttribDeclList a, PredicateDefList pdl, MethodList m, UpdateList upd, LayoutList lay, AccessDefList adl, ActionDefList actl, WorkflowDefList wl, UserTypeList u) {
//INSERTED CODE STRING:
 RESULT = new Specification(p,a,l,m,upd,lay,pdl,adl,actl,wl,u); 
//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  userTypeListOpt,
  rhs(USER, COLON, userTypeList 
/*    new Action() {
      public UserTypeList a(Object missingLabel0, Object missingLabel1, UserTypeList u) {
//INSERTED CODE STRING:
 RESULT = u;
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(
/*    new Action() {
      public UserTypeList a() {
//INSERTED CODE STRING:
 RESULT = new UserTypeList();
//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  userTypeList,
  rhs(userTypeList, COMMA, userType 
/*    new Action() {
      public UserTypeList a(UserTypeList l, Object missingLabel1, UserType u) {
//INSERTED CODE STRING:
RESULT=l.append(u);
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(userType 
/*    new Action() {
      public UserTypeList a(UserType u) {
//INSERTED CODE STRING:
RESULT=new UserTypeList(u);
//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  userType,
  rhs(IDENT, LPAREN, identListOpt, RPAREN 
/*    new Action() {
      public UserType a(String t, Object missingLabel1, StringList args, Object missingLabel3) {
//INSERTED CODE STRING:
 RESULT=new UserType(t,args);
//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  packageOpt,
  rhs(PACKAGE, packageIdent, SEMICOLON 
/*    new Action() {
      public String a(Object missingLabel0, String i, Object missingLabel2) {
//INSERTED CODE STRING:
 RESULT = i; 
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs()
)
,
prod(
  packageIdent,
  rhs(IDENT, DOT, packageIdent 
/*    new Action() {
      public String a(String i, Object missingLabel1, String pi) {
//INSERTED CODE STRING:
 RESULT = i+"."+pi; 
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(IDENT 
/*    new Action() {
      public String a(String i) {
//INSERTED CODE STRING:
 RESULT = i; 
//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  productionList,
  rhs(productionList, production 
/*    new Action() {
      public ProductionList a(ProductionList l, Production p) {
//INSERTED CODE STRING:
 RESULT = l != null ? l.append(p) : null; 
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(
/*    new Action() {
      public ProductionList a() {
//INSERTED CODE STRING:
 RESULT = new ProductionList();
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(SpecialTerminals.Error
  )
)
,
prod(
  production,
  rhs(LHS, item, rhs 
/*    new Action() {
      public Production a(String i, Item fi, Production r) {
//INSERTED CODE STRING:
 if (r instanceof RefProduction) {
         r.setType(i);
         r.setSelector(fi.getIdent());
       } else { 
         r.setName(i);
         if (r.getHasAlternatives())
           r.getAlternatives().insertElementAt(new Alternative(fi.getIdent(),null),0);
           else {
           //r.getAlternatives().elementAt(0).setName(i);
           r.getAlternatives().elementAt(0).getItems().insertElementAt(fi,0);
         }
       }
       RESULT=r;
    
//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  rhs,
  rhs(BAR, alternativeList 
/*    new Action() {
      public Production a(Object missingLabel0, AlternativeList l) {
//INSERTED CODE STRING:
 RESULT = new GrammarProduction(null,l, null, false, true); 
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(itemListOpt 
/*    new Action() {
      public Production a(ItemList l) {
//INSERTED CODE STRING:
 RESULT = new GrammarProduction(null,new AlternativeList(new Alternative(null,l)),null, false,false); 
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(ARROW, IDENT, scopeOpt, filterOpt, transmitVisitorOpt 
/*    new Action() {
      public Production a(Object missingLabel0, String r, StringList l, Formel f, Boolean b) {
//INSERTED CODE STRING:
 RESULT = new RefProduction(null,null,r,l,f,b.booleanValue()); 
//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  scopeOpt,
  rhs(SCOPE, identList 
/*    new Action() {
      public StringList a(Object missingLabel0, StringList l) {
//INSERTED CODE STRING:
RESULT=l;
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs()
)
,
prod(
  transmitVisitorOpt,
  rhs(ARROW, ARROW 
/*    new Action() {
      public Boolean a(Object missingLabel0, Object missingLabel1) {
//INSERTED CODE STRING:
RESULT=new Boolean(true);
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(
/*    new Action() {
      public Boolean a() {
//INSERTED CODE STRING:
RESULT=new Boolean(false);
//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  alternativeList,
  rhs(alternativeList, BAR, alternative 
/*    new Action() {
      public AlternativeList a(AlternativeList l, Object missingLabel1, Alternative a) {
//INSERTED CODE STRING:
 RESULT = l != null ? l.append(a) : null;  
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(alternative 
/*    new Action() {
      public AlternativeList a(Alternative a) {
//INSERTED CODE STRING:
 RESULT = new AlternativeList(a); 
//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  alternative,
  rhs(IDENT 
/*    new Action() {
      public Alternative a(String i) {
//INSERTED CODE STRING:
 RESULT = new Alternative(i,null); 
//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  nameOpt,
  rhs(IDENT 
/*    new Action() {
      public String a(String i) {
//INSERTED CODE STRING:
 RESULT = i;              
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs()
)
,
prod(
  itemListOpt,
  rhs(itemList 
/*    new Action() {
      public ItemList a(ItemList l) {
//INSERTED CODE STRING:
 RESULT = l; 
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(
/*    new Action() {
      public ItemList a() {
//INSERTED CODE STRING:
 RESULT = new ItemList(); 
//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  itemList,
  rhs(itemList, item 
/*    new Action() {
      public ItemList a(ItemList l, Item i) {
//INSERTED CODE STRING:
 RESULT = l.append(i); 
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(item 
/*    new Action() {
      public ItemList a(Item i) {
//INSERTED CODE STRING:
 RESULT = new ItemList(i); 
//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  item,
  rhs(IDENT, selectorOpt 
/*    new Action() {
      public Item a(String i, String s) {
//INSERTED CODE STRING:
 RESULT = new RecordItem(i,s,ileft);  
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(IDENT, STAR, selectorOpt 
/*    new Action() {
      public Item a(String i, Object missingLabel1, String s) {
//INSERTED CODE STRING:
 RESULT = new ListItem(i,null,s,ileft);  
//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  selectorOpt,
  rhs(selector 
/*    new Action() {
      public String a(String s) {
//INSERTED CODE STRING:
 RESULT = s; 
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs()
)
,
prod(
  selector,
  rhs(COLON, IDENT 
/*    new Action() {
      public String a(Object missingLabel0, String i) {
//INSERTED CODE STRING:
 RESULT = i; 
//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  attribList,
  rhs(attribList, attribDecl 
/*    new Action() {
      public AttribDeclList a(AttribDeclList l, AttribDecl d) {
//INSERTED CODE STRING:
 RESULT = l != null ? l.append(d) : null;  
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(
/*    new Action() {
      public AttribDeclList a() {
//INSERTED CODE STRING:
 RESULT = new AttribDeclList(); 
//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  attribDecl,
  rhs(specifier, IDENT, identListOpt, withOpt, SEMICOLON 
/*    new Action() {
      public AttribDecl a(Integer s, String t, StringList i, StringList w, Object missingLabel4) {
//INSERTED CODE STRING:
 RESULT = new AttribDecl(s,t,i,w); 
//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  identListOpt,
  rhs(identList 
/*    new Action() {
      public StringList a(StringList i) {
//INSERTED CODE STRING:
 RESULT = i; 
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs()
)
,
prod(
  identList,
  rhs(identList, COMMA, IDENT 
/*    new Action() {
      public StringList a(StringList l, Object missingLabel1, String i) {
//INSERTED CODE STRING:
 RESULT = l != null ? l.append(i) : null;  
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(IDENT 
/*    new Action() {
      public StringList a(String i) {
//INSERTED CODE STRING:
 RESULT = new StringList(i); 
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(SpecialTerminals.Error
  )
)
,
prod(
  specifier,
  rhs(INHERITED 
/*    new Action() {
      public Integer a(Object missingLabel0) {
//INSERTED CODE STRING:
 RESULT = new Integer(Specifier.INHERITED);   
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(SYNTHESIZED 
/*    new Action() {
      public Integer a(Object missingLabel0) {
//INSERTED CODE STRING:
 RESULT = new Integer(Specifier.SYNTHESIZED); 
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(ATTRIBUTE 
/*    new Action() {
      public Integer a(Object missingLabel0) {
//INSERTED CODE STRING:
 RESULT = new Integer(Specifier.ATTRIBUTE);   
//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  withOpt,
  rhs(WITH, identList 
/*    new Action() {
      public StringList a(Object missingLabel0, StringList l) {
//INSERTED CODE STRING:
 RESULT = l; 
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(WITH, SUBTREES, OF, identList 
/*    new Action() {
      public StringList a(Object missingLabel0, Object missingLabel1, Object missingLabel2, StringList l) {
//INSERTED CODE STRING:
 RESULT = l; 
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(
/*    new Action() {
      public StringList a() {
//INSERTED CODE STRING:
 RESULT = new StringList(); 
//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  methods,
  rhs(methods, method 
/*    new Action() {
      public MethodList a(MethodList l, Method m) {
//INSERTED CODE STRING:
 RESULT = l.append(m); 
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(
/*    new Action() {
      public MethodList a() {
//INSERTED CODE STRING:
 RESULT = new MethodList(); 
//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  method,
  rhs(METHODS, OF, IDENT, LBRACE, lines, RBRACE 
/*    new Action() {
      public Method a(Object missingLabel0, Object missingLabel1, String i, Object missingLabel3, LineList l, Object missingLabel5) {
//INSERTED CODE STRING:
 RESULT = new Method(i,l); 
//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  updates,
  rhs(updates, update 
/*    new Action() {
      public UpdateList a(UpdateList l, Update m) {
//INSERTED CODE STRING:
 RESULT = l.append(m); 
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(
/*    new Action() {
      public UpdateList a() {
//INSERTED CODE STRING:
 RESULT = new UpdateList(); 
//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  update,
  rhs(CHANGE, OF, IDENT, LBRACE, lines, RBRACE 
/*    new Action() {
      public Update a(Object missingLabel0, Object missingLabel1, String i, Object missingLabel3, LineList l, Object missingLabel5) {
//INSERTED CODE STRING:
 RESULT = new Update(i,l); 
//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  layouts,
  rhs(layouts, layout 
/*    new Action() {
      public LayoutList a(LayoutList l, Layout m) {
//INSERTED CODE STRING:
 RESULT = l.append(m); 
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(
/*    new Action() {
      public LayoutList a() {
//INSERTED CODE STRING:
 RESULT = new LayoutList(); 
//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  layouttype,
  rhs(ELEMENT 
/*    new Action() {
      public Integer a(Object missingLabel0) {
//INSERTED CODE STRING:
 RESULT = new Integer(LayoutType.ELEMENT);   
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(FORM 
/*    new Action() {
      public Integer a(Object missingLabel0) {
//INSERTED CODE STRING:
 RESULT = new Integer(LayoutType.FORM); 
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(UPDATE 
/*    new Action() {
      public Integer a(Object missingLabel0) {
//INSERTED CODE STRING:
 RESULT = new Integer(LayoutType.UPDATE); 
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(NODE 
/*    new Action() {
      public Integer a(Object missingLabel0) {
//INSERTED CODE STRING:
 RESULT = new Integer(LayoutType.NODE); 
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(EDGE 
/*    new Action() {
      public Integer a(Object missingLabel0) {
//INSERTED CODE STRING:
 RESULT = new Integer(LayoutType.EDGE); 
//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  layout,
  rhs(layouttype, LAYOUT, OF, IDENT, LBRACE, lines, RBRACE 
/*    new Action() {
      public Layout a(Integer t, Object missingLabel1, Object missingLabel2, String i, Object missingLabel4, LineList l, Object missingLabel6) {
//INSERTED CODE STRING:
 RESULT = new Layout(t,i,l); 
//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  lines,
  rhs(lines, LINE 
/*    new Action() {
      public LineList a(LineList ls, Line l) {
//INSERTED CODE STRING:
 RESULT = ls.append(l); 
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(
/*    new Action() {
      public LineList a() {
//INSERTED CODE STRING:
 RESULT = new LineList(); 
//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  filterOpt,
  rhs(LBRACK, formel, RBRACK 
/*    new Action() {
      public Formel a(Object missingLabel0, Formel f, Object missingLabel2) {
//INSERTED CODE STRING:
 RESULT = f; 
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(
/*    new Action() {
      public Formel a() {
//INSERTED CODE STRING:
 RESULT = null; 
//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  formel,
  rhs(IDENT 
/*    new Action() {
      public Formel a(String s) {
//INSERTED CODE STRING:
 RESULT = new IdentFormel(s); 
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(IDENT, LPAREN, RPAREN, formelRest 
/*    new Action() {
      public Formel a(String s, Object missingLabel1, Object missingLabel2, String r) {
//INSERTED CODE STRING:
 RESULT = new IdentFormel(s+"()"+r); 
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(IDENT, IS, IDENT 
/*    new Action() {
      public Formel a(String s, Object missingLabel1, String t) {
//INSERTED CODE STRING:
 RESULT = new IsFormel(s,t); 
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(IDENT, COLON, IDENT, IS, IDENT 
/*    new Action() {
      public Formel a(String u, Object missingLabel1, String t, Object missingLabel3, String v) {
//INSERTED CODE STRING:
 RESULT = new UserIsFormel(u,t,v); 
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(IDENT, LPAREN, formelList, RPAREN 
/*    new Action() {
      public Formel a(String s, Object missingLabel1, FormelList l, Object missingLabel3) {
//INSERTED CODE STRING:
 RESULT = new ApplFormel(s,l); 
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(LPAREN, formel, RPAREN 
/*    new Action() {
      public Formel a(Object missingLabel0, Formel f, Object missingLabel2) {
//INSERTED CODE STRING:
 RESULT = f; 
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(NOT, formel 
/*    new Action() {
      public Formel a(Object missingLabel0, Formel f) {
//INSERTED CODE STRING:
 RESULT = new NotFormel(f); 
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(formel, formelOp, formel 
/*    new Action() {
      public Formel a(Formel l, Integer o, Formel r) {
//INSERTED CODE STRING:
 RESULT = new BinFormel(l,o,r); 
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(formel, ARROW, formel 
/*    new Action() {
      public Formel a(Formel l, Object missingLabel1, Formel r) {
//INSERTED CODE STRING:
 RESULT = new BinFormel(l,new Integer(FormelOp.OR),new NotFormel(r)); 
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(LBRACE, lines, RBRACE 
/*    new Action() {
      public Formel a(Object missingLabel0, LineList l, Object missingLabel2) {
//INSERTED CODE STRING:
 RESULT = new JavaFormel(l); 
//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  formelRest,
  rhs(DOT, IDENT, LPAREN, RPAREN, formelRest 
/*    new Action() {
      public String a(Object missingLabel0, String s, Object missingLabel2, Object missingLabel3, String r) {
//INSERTED CODE STRING:
 RESULT = new String("."+s+"()"+r);   
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(
/*    new Action() {
      public String a() {
//INSERTED CODE STRING:
 RESULT = new String(""); 
//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  formelOp,
  rhs(AND 
/*    new Action() {
      public Integer a(Object missingLabel0) {
//INSERTED CODE STRING:
 RESULT = new Integer(FormelOp.AND);   
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(BAR 
/*    new Action() {
      public Integer a(Object missingLabel0) {
//INSERTED CODE STRING:
 RESULT = new Integer(FormelOp.OR); 
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(EQUALS 
/*    new Action() {
      public Integer a(Object missingLabel0) {
//INSERTED CODE STRING:
 RESULT = new Integer(FormelOp.EQUALS); 
//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  formelListOpt,
  rhs(formelList 
/*    new Action() {
      public FormelList a(FormelList i) {
//INSERTED CODE STRING:
 RESULT = i; 
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs()
)
,
prod(
  formelList,
  rhs(formelList, COMMA, formel 
/*    new Action() {
      public FormelList a(FormelList l, Object missingLabel1, Formel i) {
//INSERTED CODE STRING:
 RESULT = l != null ? l.append(i) : null;  
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(formel 
/*    new Action() {
      public FormelList a(Formel i) {
//INSERTED CODE STRING:
 RESULT = new FormelList(i); 
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(SpecialTerminals.Error
  )
)
,
prod(
  predicateDefList,
  rhs(predicateDefList, predicateDef 
/*    new Action() {
      public PredicateDefList a(PredicateDefList l, PredicateDef m) {
//INSERTED CODE STRING:
 RESULT = l.append(m); 
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(
/*    new Action() {
      public PredicateDefList a() {
//INSERTED CODE STRING:
 RESULT = new PredicateDefList(); 
//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  predicateDef,
  rhs(PREDICATES, OF, IDENT, LBRACE, predicateList, RBRACE 
/*    new Action() {
      public PredicateDef a(Object missingLabel0, Object missingLabel1, String i, Object missingLabel3, PredicateList l, Object missingLabel5) {
//INSERTED CODE STRING:
 RESULT = new PredicateDef(i,l); 
//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  predicateList,
  rhs(predicateList, predicate 
/*    new Action() {
      public PredicateList a(PredicateList l, Predicate m) {
//INSERTED CODE STRING:
 RESULT = l.append(m); 
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(
/*    new Action() {
      public PredicateList a() {
//INSERTED CODE STRING:
 RESULT = new PredicateList(); 
//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  predicate,
  rhs(IDENT, EQUALS, formel, SEMICOLON 
/*    new Action() {
      public Predicate a(String l, Object missingLabel1, Formel f, Object missingLabel3) {
//INSERTED CODE STRING:
 RESULT = new Predicate(l,f); 
//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  accessDefList,
  rhs(accessDefList, accessDef 
/*    new Action() {
      public AccessDefList a(AccessDefList l, AccessDef m) {
//INSERTED CODE STRING:
 RESULT = l.append(m); 
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(
/*    new Action() {
      public AccessDefList a() {
//INSERTED CODE STRING:
 RESULT = new AccessDefList(); 
//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  accessDef,
  rhs(ACCESS, OF, IDENT, LBRACE, accessRuleList, RBRACE 
/*    new Action() {
      public AccessDef a(Object missingLabel0, Object missingLabel1, String i, Object missingLabel3, AccessRuleList l, Object missingLabel5) {
//INSERTED CODE STRING:
 RESULT = new AccessDef(i,l); 
//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  accessRuleList,
  rhs(accessRuleList, accessRule 
/*    new Action() {
      public AccessRuleList a(AccessRuleList l, AccessRule ar) {
//INSERTED CODE STRING:
 RESULT = l.append(ar); 
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(
/*    new Action() {
      public AccessRuleList a() {
//INSERTED CODE STRING:
 RESULT = new AccessRuleList(); 
//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  accessRule,
  rhs(LPAREN, IDENT, COMMA, formel, RPAREN, COLON, accessEntryList, SEMICOLON 
/*    new Action() {
      public AccessRule a(Object missingLabel0, String p, Object missingLabel2, Formel f, Object missingLabel4, Object missingLabel5, AccessEntryList e, Object missingLabel7) {
//INSERTED CODE STRING:
 RESULT = new AccessRule(p.equals("_")?"start":p,f,e); 
//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  accessEntryList,
  rhs(accessEntryList, COMMA, accessEntry 
/*    new Action() {
      public AccessEntryList a(AccessEntryList l, Object missingLabel1, AccessEntry ae) {
//INSERTED CODE STRING:
 RESULT = l.append(ae); 
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(accessEntry 
/*    new Action() {
      public AccessEntryList a(AccessEntry ae) {
//INSERTED CODE STRING:
 RESULT = new AccessEntryList(ae); 
//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  accessEntry,
  rhs(accessOp, IDENT 
/*    new Action() {
      public AccessEntry a(Integer o, String i) {
//INSERTED CODE STRING:
 RESULT = new AccessEntry(o,i); 
//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  accessOp,
  rhs(READ 
/*    new Action() {
      public Integer a(Object missingLabel0) {
//INSERTED CODE STRING:
 RESULT = new Integer(AccessOp.READ);   
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(WRITE 
/*    new Action() {
      public Integer a(Object missingLabel0) {
//INSERTED CODE STRING:
 RESULT = new Integer(AccessOp.WRITE); 
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(EXEC 
/*    new Action() {
      public Integer a(Object missingLabel0) {
//INSERTED CODE STRING:
 RESULT = new Integer(AccessOp.WRITE); 
//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  actionDefList,
  rhs(actionDefList, actionDef 
/*    new Action() {
      public ActionDefList a(ActionDefList l, ActionDef m) {
//INSERTED CODE STRING:
 RESULT = l.append(m); 
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(
/*    new Action() {
      public ActionDefList a() {
//INSERTED CODE STRING:
 RESULT = new ActionDefList(); 
//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  actionDef,
  rhs(ACTIONS, OF, IDENT, LBRACE, actionRuleList, RBRACE 
/*    new Action() {
      public ActionDef a(Object missingLabel0, Object missingLabel1, String i, Object missingLabel3, ActionRuleList l, Object missingLabel5) {
//INSERTED CODE STRING:
 RESULT = new ActionDef(i,l); 
//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  actionRuleList,
  rhs(actionRuleList, actionRule 
/*    new Action() {
      public ActionRuleList a(ActionRuleList l, ActionRule ar) {
//INSERTED CODE STRING:
 RESULT = l.append(ar); 
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(
/*    new Action() {
      public ActionRuleList a() {
//INSERTED CODE STRING:
 RESULT = new ActionRuleList(); 
//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  actionRule,
  rhs(actionOp, IDENT, precondition, LPAREN, argumentList, RPAREN, preActionResult, actionResultList, SEMICOLON 
/*    new Action() {
      public ActionRule a(Integer o, String i, Formel p, Object missingLabel3, ArgumentList args, Object missingLabel5, PreActionResult pre, ActionResultList a, Object missingLabel8) {
//INSERTED CODE STRING:
 RESULT = new ActionRule(o,i,p,args,pre,a);
//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  precondition,
  rhs(LBRACK, formel, RBRACK 
/*    new Action() {
      public Formel a(Object missingLabel0, Formel f, Object missingLabel2) {
//INSERTED CODE STRING:
 RESULT = f; 
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(LBRACK, RBRACK 
/*    new Action() {
      public Formel a(Object missingLabel0, Object missingLabel1) {
//INSERTED CODE STRING:
 RESULT = new IdentFormel("true"); 
//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  actionOp,
  rhs(SINGLE 
/*    new Action() {
      public Integer a(Object missingLabel0) {
//INSERTED CODE STRING:
 RESULT = new Integer(ActionOp.SINGLE);   
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(MULT 
/*    new Action() {
      public Integer a(Object missingLabel0) {
//INSERTED CODE STRING:
 RESULT = new Integer(ActionOp.MULT); 
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(ATOM 
/*    new Action() {
      public Integer a(Object missingLabel0) {
//INSERTED CODE STRING:
 RESULT = new Integer(ActionOp.ATOM); 
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(
/*    new Action() {
      public Integer a() {
//INSERTED CODE STRING:
 RESULT = new Integer(ActionOp.SINGLE); 
//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  argumentList,
  rhs(argumentList, argument 
/*    new Action() {
      public ArgumentList a(ArgumentList l, Argument i) {
//INSERTED CODE STRING:
 RESULT = l != null ? l.append(i) : null; 
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(
/*    new Action() {
      public ArgumentList a() {
//INSERTED CODE STRING:
 RESULT = new ArgumentList(); 
//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  argument,
  rhs(IDENT, argumentOpt 
/*    new Action() {
      public Argument a(String i, String s) {
//INSERTED CODE STRING:
 RESULT = new Argument(i,(s==null?i:s));  
//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  argumentOpt,
  rhs(COLON, IDENT 
/*    new Action() {
      public String a(Object missingLabel0, String s) {
//INSERTED CODE STRING:
 RESULT = s; 
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs()
)
,
prod(
  actionResultList,
  rhs(actionResult 
/*    new Action() {
      public ActionResultList a(ActionResult a) {
//INSERTED CODE STRING:
 RESULT = new ActionResultList(a); 
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(actionResultList, COMMA, actionResult 
/*    new Action() {
      public ActionResultList a(ActionResultList l, Object missingLabel1, ActionResult i) {
//INSERTED CODE STRING:
 RESULT = l.append(i);  
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(
/*    new Action() {
      public ActionResultList a() {
//INSERTED CODE STRING:
 RESULT = new ActionResultList();  
//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  actionResult,
  rhs(LBRACK, formel, RBRACK, IDENT, STARTACTIONCODE, lines, RBRACE, actionAlt 
/*    new Action() {
      public ActionResult a(Object missingLabel0, Formel f, Object missingLabel2, String i, Object missingLabel4, LineList l, Object missingLabel6, StringList a) {
//INSERTED CODE STRING:
 RESULT = new ActionResult(f,i,l,a);  
//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  actionAlt,
  rhs(IDENT 
/*    new Action() {
      public StringList a(String i) {
//INSERTED CODE STRING:
 RESULT = new StringList(i);  
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(actionAlt, BAR, IDENT 
/*    new Action() {
      public StringList a(StringList l, Object missingLabel1, String i) {
//INSERTED CODE STRING:
 RESULT = l.append(i);  
//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  preActionResult,
  rhs(STARTACTIONCODE, lines, RBRACE, actionAlt 
/*    new Action() {
      public PreActionResult a(Object missingLabel0, LineList l, Object missingLabel2, StringList a) {
//INSERTED CODE STRING:
 RESULT = new PreActionResult(l,a);  
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs()
)
,
prod(
  workflowDefList,
  rhs(workflowDefList, workflowDef 
/*    new Action() {
      public WorkflowDefList a(WorkflowDefList l, WorkflowDef m) {
//INSERTED CODE STRING:
 RESULT = l.append(m); 
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(
/*    new Action() {
      public WorkflowDefList a() {
//INSERTED CODE STRING:
 RESULT = new WorkflowDefList(); 
//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  workflowDef,
  rhs(WORKFLOW, OF, IDENT, LBRACE, transitionList, RBRACE 
/*    new Action() {
      public WorkflowDef a(Object missingLabel0, Object missingLabel1, String i, Object missingLabel3, TransitionList l, Object missingLabel5) {
//INSERTED CODE STRING:
 RESULT = new WorkflowDef(i,l); 
//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  transitionList,
  rhs(transitionList, transition 
/*    new Action() {
      public TransitionList a(TransitionList l, Transition t) {
//INSERTED CODE STRING:
 RESULT = l.append(t); 
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(
/*    new Action() {
      public TransitionList a() {
//INSERTED CODE STRING:
 RESULT = new TransitionList(); 
//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  transition,
  rhs(ARROW, IDENT 
/*    new Action() {
      public Transition a(Object missingLabel0, String post) {
//INSERTED CODE STRING:
 RESULT = new Transition("",true,"",post); 
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(IDENT, ARROW, IDENT, ARROW, IDENT 
/*    new Action() {
      public Transition a(String pre, Object missingLabel1, String token, Object missingLabel3, String post) {
//INSERTED CODE STRING:
 RESULT = new Transition(pre,false,token,post); 
//END INSERT
      return RESULT; }
    };*/
  )
)
);
}
}
