package edu.tum.cup2.test;

import java.io.File;

import org.junit.Test;

import edu.tum.cup2.generator.LR1Generator;
import edu.tum.cup2.generator.Verbosity;
import edu.tum.cup2.generator.exceptions.GeneratorException;
import edu.tum.cup2.grammar.NonTerminal;
import edu.tum.cup2.grammar.Terminal;
import edu.tum.cup2.io.LRParsingTableDump;
import edu.tum.cup2.parser.tables.LRParsingTable;
import edu.tum.cup2.semantics.SymbolValue;
import edu.tum.cup2.spec.CUPSpecification;

//locate static imports after others to be compatible with javac
import static edu.tum.cup2.grammar.SpecialTerminals.Epsilon;
import static edu.tum.cup2.test.SpecJava14.NonTerminals.*;
import static edu.tum.cup2.test.SpecJava14.Terminals.*;
import static junit.framework.Assert.fail;

/**
 * Specification of the Java 1.4 grammar.
 * Based on the Java 1.4 grammar from the old CUP.
 * 
 * @author Andreas Wenger
 */
public class SpecJava14
	extends CUPSpecification
{

	
	//terminals
	public enum Terminals implements Terminal
	{
		BOOLEAN,
		BYTE, SHORT, INT, LONG, CHAR,
		FLOAT, DOUBLE,
		LBRACK, RBRACK,
		IDENTIFIER,
		DOT,
		SEMICOLON, MULT, COMMA, LBRACE, RBRACE, EQ, LPAREN, RPAREN, COLON,
		PACKAGE,
		IMPORT,
		PUBLIC, PROTECTED, PRIVATE,
		STATIC,
		ABSTRACT, FINAL, NATIVE, SYNCHRONIZED, TRANSIENT, VOLATILE,
		CLASS,
		EXTENDS,
		IMPLEMENTS,
		VOID,
		THROWS,
		THIS, SUPERKEYWORD,
		INTERFACE,
		IF, ELSE,
		SWITCH,
		CASE, DEFAULT,
		DO, WHILE,
		FOR,
		BREAK,
		CONTINUE,
		RETURN,
		THROW,
		TRY,
		CATCH,
		FINALLY,
		NEW,
		PLUSPLUS,
		MINUSMINUS,
		PLUS, MINUS, COMP, NOT, DIV, MOD,
		LSHIFT, RSHIFT, URSHIFT,
		LT, GT, LTEQ, GTEQ, INSTANCEOF,
		EQEQ, NOTEQ,
		AND,
		XOR,
		OR,
		ANDAND,
		OROR,
		QUESTION,
		MULTEQ, DIVEQ, MODEQ, PLUSEQ, MINUSEQ,
		LSHIFTEQ, RSHIFTEQ, URSHIFTEQ,
		ANDEQ, XOREQ, OREQ,
		INTEGER_LITERAL,
		FLOATING_POINT_LITERAL,
		BOOLEAN_LITERAL,
		CHARACTER_LITERAL,
		STRING_LITERAL,
		NULL_LITERAL,
		CONST,
		GOTO,
		STRICTFP,
		ASSERT,
		ELLIPSIS,
		ENUM
	}


	//non-terminals
	public enum NonTerminals implements NonTerminal
	{
		goal,
		literal,
		type, primitive_type, numeric_type,
		integral_type, floating_point_type,
		reference_type,
		class_or_interface_type,
		class_type, interface_type,
		array_type,
		name, simple_name, qualified_name,
		compilation_unit,
		package_declaration_opt, package_declaration,
		import_declarations_opt, import_declarations,
		type_declarations_opt, type_declarations,
		import_declaration,
		single_type_import_declaration,
		type_import_on_demand_declaration,
		type_declaration,
		modifiers_opt, modifiers, modifier,
		class_declaration, supernonterm, super_opt,
		interfaces, interfaces_opt, interface_type_list,
		class_body,
		class_body_declarations, class_body_declarations_opt,
		class_body_declaration, class_member_declaration,
		field_declaration, variable_declarators, variable_declarator,
		variable_declarator_id, variable_initializer,
		method_declaration, method_header, method_declarator,
		formal_parameter_list_opt, formal_parameter_list,
		formal_parameter,
		throws_opt, throws_,
		class_type_list, method_body,
		static_initializer,
		constructor_declaration, constructor_declarator,
		constructor_body,
		explicit_constructor_invocation,
		interface_declaration,
		extends_interfaces_opt, extends_interfaces,
		interface_body,
		interface_member_declarations_opt, interface_member_declarations,
		interface_member_declaration, constant_declaration,
		abstract_method_declaration,
		array_initializer,
		variable_initializers,
		block,
		block_statements_opt, block_statements, block_statement,
		local_variable_declaration_statement, local_variable_declaration,
		statement, statement_no_short_if,
		statement_without_trailing_substatement,
		empty_statement,
		labeled_statement, labeled_statement_no_short_if,
		expression_statement, statement_expression,
		if_then_statement,
		if_then_else_statement, if_then_else_statement_no_short_if,
		switch_statement, switch_block,
		switch_block_statement_groups,
		switch_block_statement_group,
		switch_labels, switch_label,
		while_statement, while_statement_no_short_if,
		do_statement,
		for_statement, for_statement_no_short_if,
		for_init_opt, for_init,
		for_update_opt, for_update,
		statement_expression_list,
		identifier_opt,
		break_statement, continue_statement,
		return_statement, throw_statement,
		synchronized_statement, try_statement,
		catches_opt, catches, catch_clause,
		finally_,
		assert_statement,
		primary, primary_no_new_array,
		class_instance_creation_expression,
		argument_list_opt, argument_list,
		array_creation_init, array_creation_uninit,
		dim_exprs, dim_expr, dims_opt, dims,
		field_access, method_invocation, array_access,
		postfix_expression,
		postincrement_expression, postdecrement_expression,
		unary_expression, unary_expression_not_plus_minus,
		preincrement_expression, predecrement_expression,
		cast_expression,
		multiplicative_expression, additive_expression,
		shift_expression, relational_expression, equality_expression,
		and_expression, exclusive_or_expression, inclusive_or_expression,
		conditional_and_expression, conditional_or_expression,
		conditional_expression, assignment_expression,
		assignment,
		assignment_operator,
		expression_opt, expression,
		constant_expression
	}
	
	
	//symbols with values
	public class IDENTIFIER               extends SymbolValue<String>{};
	public class INTEGER_LITERAL          extends SymbolValue<Number>{};
	public class FLOATING_POINT_LITERAL   extends SymbolValue<Number>{};
	public class BOOLEAN_LITERAL          extends SymbolValue<Boolean>{};
	public class CHARACTER_LITERAL        extends SymbolValue<Character>{};
	public class STRING_LITERAL           extends SymbolValue<String>{};

	
	public SpecJava14()
	{
		grammar(
			prod(goal,
			            rhs(compilation_unit)),
			prod(literal,
				          rhs(INTEGER_LITERAL),
				          rhs(FLOATING_POINT_LITERAL),
				          rhs(BOOLEAN_LITERAL),
				          rhs(CHARACTER_LITERAL),
				          rhs(STRING_LITERAL),
				          rhs(NULL_LITERAL)),
			prod(type,
				          rhs(primitive_type),
				          rhs(reference_type)),
			prod(primitive_type,
				          rhs(numeric_type),
				          rhs(BOOLEAN)),
			prod(numeric_type,
				          rhs(integral_type),
				          rhs(floating_point_type)),
			prod(integral_type,
				          rhs(BYTE),
				          rhs(SHORT),
				          rhs(INT),
				          rhs(LONG),
				          rhs(CHAR)),
			prod(floating_point_type,
				          rhs(FLOAT),
				          rhs(DOUBLE)),
			prod(reference_type,
				          rhs(class_or_interface_type),
				          rhs(array_type)),
			prod(class_or_interface_type,
				          rhs(name)),
			prod(class_type,
				          rhs(class_or_interface_type)),
			prod(interface_type,
				          rhs(class_or_interface_type)),
			prod(array_type,
				          rhs(primitive_type, dims),
				          rhs(name, dims)),
			prod(name,
				          rhs(simple_name),
				          rhs(qualified_name)),
			prod(simple_name,
				          rhs(IDENTIFIER)),
			prod(qualified_name,
				          rhs(name, DOT, IDENTIFIER)),
			prod(compilation_unit,
				          rhs(package_declaration_opt, import_declarations_opt, type_declarations_opt)),
			prod(package_declaration_opt,
				          rhs(package_declaration),
				          rhs()),
			prod(import_declarations_opt,
				          rhs(import_declarations),
				          rhs()),
			prod(type_declarations_opt,
				          rhs(type_declarations),
				          rhs()),
			prod(import_declarations,
				          rhs(import_declaration),
				          rhs(import_declarations, import_declaration)),
			prod(type_declarations,
				          rhs(type_declaration),
				          rhs(type_declarations, type_declaration)),
			prod(package_declaration,
				          rhs(PACKAGE, name, SEMICOLON)),
			prod(import_declaration,
				          rhs(single_type_import_declaration),
				          rhs(type_import_on_demand_declaration)),
			prod(single_type_import_declaration,
				          rhs(IMPORT, name, SEMICOLON)),
			prod(type_import_on_demand_declaration,
				          rhs(IMPORT, name, DOT, MULT, SEMICOLON)),
			prod(type_declaration,
				          rhs(class_declaration), //50
				          rhs(interface_declaration),
				          rhs(SEMICOLON)),
			prod(modifiers_opt,
				          rhs(modifiers),
				          rhs()),
			prod(modifiers,
				          rhs(modifier),
				          rhs(modifiers, modifier)),
			prod(modifier,
				          rhs(PUBLIC),
				          rhs(PROTECTED),
				          rhs(PRIVATE),
				          rhs(STATIC),
				          rhs(ABSTRACT),
				          rhs(FINAL),
				          rhs(NATIVE),
				          rhs(SYNCHRONIZED),
				          rhs(TRANSIENT),
				          rhs(VOLATILE),
				          rhs(STRICTFP)),
			prod(class_declaration,
				          rhs(modifiers_opt, CLASS, IDENTIFIER, super_opt, interfaces_opt, class_body)),
			prod(supernonterm,
				          rhs(EXTENDS, class_type)),
			prod(super_opt,
				          rhs(supernonterm),
				          rhs()),
			prod(interfaces,
				          rhs(IMPLEMENTS, interface_type_list)),
			prod(interfaces_opt,
				          rhs(interfaces),
				          rhs()),
			prod(interface_type_list,
				          rhs(interface_type),
				          rhs(interface_type_list, COMMA, interface_type)),
			prod(class_body,
				          rhs(LBRACE, class_body_declarations_opt, RBRACE)),
			prod(class_body_declarations_opt,
				          rhs(class_body_declarations),
				          rhs()),
			prod(class_body_declarations,
				          rhs(class_body_declaration),
				          rhs(class_body_declarations, class_body_declaration)),
			prod(class_body_declaration,
				          rhs(class_member_declaration),
				          rhs(static_initializer),
				          rhs(constructor_declaration),
				          rhs(block)),
			prod(class_member_declaration,
				          rhs(field_declaration),
				          rhs(method_declaration),
				          rhs(modifiers_opt, CLASS, IDENTIFIER, super_opt, interfaces_opt, class_body),
				          rhs(interface_declaration),
				          rhs(SEMICOLON)),
			prod(field_declaration,
				          rhs(modifiers_opt, type, variable_declarators, SEMICOLON)),
			prod(variable_declarators,
				          rhs(variable_declarator),
				          rhs(variable_declarators, COMMA, variable_declarator)),
			prod(variable_declarator,
				          rhs(variable_declarator_id),
				          rhs(variable_declarator_id, EQ, variable_initializer)),
			prod(variable_declarator_id,
				          rhs(IDENTIFIER),
				          rhs(variable_declarator_id, LBRACK, RBRACK)),
			prod(variable_initializer,
				          rhs(expression),
				          rhs(array_initializer)),
			prod(method_declaration,
				          rhs(method_header, method_body)), //100
			prod(method_header,
				          rhs(modifiers_opt, type, method_declarator, throws_opt),
				          rhs(modifiers_opt, VOID, method_declarator, throws_opt)),
			prod(method_declarator,
				          rhs(IDENTIFIER, LPAREN, formal_parameter_list_opt, RPAREN),
				          rhs(method_declarator, LBRACK, RBRACK)),
			prod(formal_parameter_list_opt,
				          rhs(formal_parameter_list),
				          rhs()),
			prod(formal_parameter_list,
				          rhs(formal_parameter),
				          rhs(formal_parameter_list, COMMA, formal_parameter)),
			prod(formal_parameter,
				          rhs(type, variable_declarator_id),
				          rhs(FINAL, type, variable_declarator_id)),
			prod(throws_opt,
				          rhs(throws_),
				          rhs()),
			prod(throws_,
				          rhs(THROWS, class_type_list)),
			prod(class_type_list,
				          rhs(class_type),
				          rhs(class_type_list, COMMA, class_type)),
			prod(method_body,
				          rhs(block),
				          rhs(SEMICOLON)),
			prod(static_initializer,
				          rhs(STATIC, block)),
			prod(constructor_declaration,
				          rhs(modifiers_opt, constructor_declarator, throws_opt, constructor_body)),
			prod(constructor_declarator,
				          rhs(simple_name, LPAREN, formal_parameter_list_opt, RPAREN)),
			prod(constructor_body,
				          rhs(LBRACE, explicit_constructor_invocation, block_statements, RBRACE),
				          rhs(LBRACE, explicit_constructor_invocation, RBRACE),
				          rhs(LBRACE, block_statements, RBRACE),
				          rhs(LBRACE, RBRACE)),
			prod(explicit_constructor_invocation,
				          rhs(THIS, LPAREN, argument_list_opt, RPAREN, SEMICOLON),
				          rhs(SUPERKEYWORD, LPAREN, argument_list_opt, RPAREN, SEMICOLON),
				          rhs(primary, DOT, THIS, LPAREN, argument_list_opt, RPAREN, SEMICOLON),
				          rhs(primary, DOT, SUPERKEYWORD, LPAREN, argument_list_opt, RPAREN, SEMICOLON)),
			prod(interface_declaration,
				          rhs(modifiers_opt, INTERFACE, IDENTIFIER, extends_interfaces_opt, interface_body)),
			prod(extends_interfaces_opt,
				          rhs(extends_interfaces),
				          rhs()),
			prod(extends_interfaces,
				          rhs(EXTENDS, interface_type),
				          rhs(extends_interfaces, COMMA, interface_type)),
			prod(interface_body,
				          rhs(LBRACE, interface_member_declarations_opt, RBRACE)),
			prod(interface_member_declarations_opt,
				          rhs(interface_member_declarations),
				          rhs()),
			prod(interface_member_declarations,
				          rhs(interface_member_declaration),
				          rhs(interface_member_declarations, interface_member_declaration)),
			prod(interface_member_declaration,
				          rhs(constant_declaration),
				          rhs(abstract_method_declaration),
				          rhs(class_declaration),
				          rhs(interface_declaration),
				          rhs(SEMICOLON)),
			prod(constant_declaration,
				          rhs(field_declaration)),
			prod(abstract_method_declaration,
				          rhs(method_header, SEMICOLON)),
			prod(array_initializer,
				          rhs(LBRACE, variable_initializers, COMMA, RBRACE),
				          rhs(LBRACE, variable_initializers, RBRACE),
				          rhs(LBRACE, COMMA, RBRACE),
				          rhs(LBRACE, RBRACE)),
			prod(variable_initializers,
				          rhs(variable_initializer), //150
				          rhs(variable_initializers, COMMA, variable_initializer)),
			prod(block,
				          rhs(LBRACE, block_statements_opt, RBRACE)),
			prod(block_statements_opt,
				          rhs(block_statements),
				          rhs()),
			prod(block_statements,
				          rhs(block_statement),
				          rhs(block_statements, block_statement)),
		  prod(block_statement,
		  	          rhs(local_variable_declaration_statement),
		  	          rhs(statement),
		  	          rhs(class_declaration),
		  	          rhs(interface_declaration)),
		  prod(local_variable_declaration_statement,
		  	          rhs(local_variable_declaration, SEMICOLON)),
		  prod(local_variable_declaration,
		  	          rhs(type, variable_declarators),
		  	          rhs(FINAL, type, variable_declarators)),
		  prod(statement,
		  	          rhs(statement_without_trailing_substatement),
		  	          rhs(labeled_statement),
		  	          rhs(if_then_statement),
		  	          rhs(if_then_else_statement),
		  	          rhs(while_statement),
		  	          rhs(for_statement)),
		  prod(statement_no_short_if,
		  	          rhs(statement_without_trailing_substatement),
		  	          rhs(labeled_statement_no_short_if),
		  	          rhs(if_then_else_statement_no_short_if),
		  	          rhs(while_statement_no_short_if),
		  	          rhs(for_statement_no_short_if)),
		  prod(statement_without_trailing_substatement,
		  	          rhs(block),
		  	          rhs(empty_statement),
		  	          rhs(expression_statement),
		  	          rhs(switch_statement),
		  	          rhs(do_statement),
		  	          rhs(break_statement),
		  	          rhs(continue_statement),
		  	          rhs(return_statement),
		  	          rhs(synchronized_statement),
		  	          rhs(throw_statement),
		  	          rhs(try_statement),
		  	          rhs(assert_statement)),
		  prod(empty_statement,
		  	          rhs(SEMICOLON)),
		  prod(labeled_statement,
		  	          rhs(IDENTIFIER, COLON, statement)),
		  prod(labeled_statement_no_short_if,
		  	          rhs(IDENTIFIER, COLON, statement_no_short_if)),
		  prod(expression_statement,
		  	          rhs(statement_expression, SEMICOLON)),
		  prod(statement_expression,
		  	          rhs(assignment),
		  	          rhs(preincrement_expression),
		  	          rhs(predecrement_expression),
		  	          rhs(postincrement_expression),
		  	          rhs(postdecrement_expression),
		  	          rhs(method_invocation),
		  	          rhs(class_instance_creation_expression)),
		  prod(if_then_statement,
		  	          rhs(IF, LPAREN, expression, RPAREN, statement)),
		  prod(if_then_else_statement,
		  	          rhs(IF, LPAREN, expression, RPAREN, statement_no_short_if, 
		  	      			ELSE, statement)),
		  prod(if_then_else_statement_no_short_if,
		  	          rhs(IF, LPAREN, expression, RPAREN, statement_no_short_if,
		  	          	ELSE, statement_no_short_if)), //200
		  prod(switch_statement,
		  	          rhs(SWITCH, LPAREN, expression, RPAREN, switch_block)),
		  prod(switch_block,
		  	          rhs(LBRACE, switch_block_statement_groups, switch_labels, RBRACE),
		  	          rhs(LBRACE, switch_block_statement_groups, RBRACE),
		  	          rhs(LBRACE, switch_labels, RBRACE),
		  	          rhs(LBRACE, RBRACE)),
		  prod(switch_block_statement_groups,
		  	          rhs(switch_block_statement_group),
		  	          rhs(switch_block_statement_groups, switch_block_statement_group)),
		  prod(switch_block_statement_group,
		  	          rhs(switch_labels, block_statements)),
		  prod(switch_labels,
		  	          rhs(switch_label),
		  	          rhs(switch_labels, switch_label)),
		  prod(switch_label,
		              rhs(CASE, constant_expression, COLON),
		              rhs(DEFAULT, COLON)),
		  prod(while_statement,
		  	          rhs(WHILE, LPAREN, expression, RPAREN, statement)),
		  prod(while_statement_no_short_if,
		  	          rhs(WHILE, LPAREN, expression, RPAREN, statement_no_short_if)),
		  prod(do_statement,
		  	          rhs(DO, statement, WHILE, LPAREN, expression, RPAREN, SEMICOLON)),
		  prod(for_statement,
		  	          rhs(FOR, LPAREN, for_init_opt, SEMICOLON, expression_opt, SEMICOLON,
		  	      			for_update_opt, RPAREN, statement)),
		  prod(for_statement_no_short_if,
		  	          rhs(FOR, LPAREN, for_init_opt, SEMICOLON, expression_opt, SEMICOLON,
		  	      			for_update_opt, RPAREN, statement_no_short_if)),
		  prod(for_init_opt,
		  	          rhs(for_init),
		  	          rhs()),
		  prod(for_init,
		  	          rhs(statement_expression_list),
		  	          rhs(local_variable_declaration)),
		  prod(for_update_opt,
		  	          rhs(for_update),
		  	          rhs()),
		  prod(for_update,
		  	          rhs(statement_expression_list)),
		  prod(statement_expression_list,
		  	          rhs(statement_expression),
		  	          rhs(statement_expression_list, COMMA, statement_expression)),
		  prod(identifier_opt,
		  	          rhs(IDENTIFIER),
		  	          rhs()),
		  prod(break_statement,
		  	          rhs(BREAK, identifier_opt, SEMICOLON)),
		  prod(continue_statement,
		  	          rhs(CONTINUE, identifier_opt, SEMICOLON)),
		  prod(return_statement,
		  	          rhs(RETURN, expression_opt, SEMICOLON)),
		  prod(throw_statement,
		  	          rhs(THROW, expression, SEMICOLON)),
		  prod(synchronized_statement,
		  	          rhs(SYNCHRONIZED, LPAREN, expression, RPAREN, block)),
		  prod(try_statement,
		  	          rhs(TRY, block, catches),
		  	          rhs(TRY, block, catches_opt, finally_)),
		  prod(catches_opt,
		  	          rhs(catches),
		  	          rhs()),
		  prod(catches,
		  	          rhs(catch_clause),
		  	          rhs(catches, catch_clause)),
		  prod(catch_clause,
		  	          rhs(CATCH, LPAREN, formal_parameter, RPAREN, block)),
		  prod(finally_,
		  	          rhs(FINALLY, block)),
		  prod(assert_statement,
		  	          rhs(ASSERT, expression, SEMICOLON),
		  	          rhs(ASSERT, expression, COLON, expression, SEMICOLON)),
		  prod(primary,
		  	          rhs(primary_no_new_array),
		  	          rhs(array_creation_init),
		  	          rhs(array_creation_uninit)),
		  prod(primary_no_new_array,
		  	          rhs(literal),
		  	          rhs(THIS),
		  	          rhs(LPAREN, expression, RPAREN),
		  	          rhs(class_instance_creation_expression), //250
		  	          rhs(field_access),
		  	          rhs(method_invocation),
		  	          rhs(array_access),
		  	          rhs(primitive_type, DOT, CLASS),
		  	          rhs(VOID, DOT, CLASS),
		  	          rhs(array_type, DOT, CLASS),
		  	          rhs(name, DOT, CLASS),
		  	          rhs(name, DOT, THIS)),
		  prod(class_instance_creation_expression,
		  	          rhs(NEW, class_or_interface_type, LPAREN, argument_list_opt, RPAREN),
		  	          rhs(NEW, class_or_interface_type, LPAREN, argument_list_opt, RPAREN, class_body),
		  	          rhs(primary, DOT, NEW, IDENTIFIER,
		  	          	LPAREN, argument_list_opt, RPAREN),
		  	          rhs(primary, DOT, NEW, IDENTIFIER,
		  	          	LPAREN, argument_list_opt, RPAREN, class_body),
		  	          rhs(name, DOT, NEW, IDENTIFIER,
		  	          	LPAREN, argument_list_opt, RPAREN),
		  	          rhs(name, DOT, NEW, IDENTIFIER,
		  	          	LPAREN, argument_list_opt, RPAREN, class_body)),
		  prod(argument_list_opt,
		  	          rhs(argument_list),
		  	          rhs()),
		  prod(argument_list,
		  	          rhs(expression),
		  	          rhs(argument_list, COMMA, expression)),
		  prod(array_creation_uninit,
		  	          rhs(NEW, primitive_type, dim_exprs, dims_opt),
		  	          rhs(NEW, class_or_interface_type, dim_exprs, dims_opt)),
		  prod(array_creation_init,
		  	          rhs(NEW, primitive_type, dims, array_initializer),
		  	          rhs(NEW, class_or_interface_type, dims, array_initializer)),
		  prod(dim_exprs,
		  	          rhs(dim_expr),
		  	          rhs(dim_exprs, dim_expr)),
		  prod(dim_expr,
		  	          rhs(LBRACK, expression, RBRACK)),
		  prod(dims_opt,
		  	          rhs(dims),
		  	          rhs()),
		  prod(dims,
		  	          rhs(LBRACK, RBRACK),
		  	          rhs(dims, LBRACK, RBRACK)),
		  prod(field_access,
		  	          rhs(primary, DOT, IDENTIFIER),
		  	          rhs(SUPERKEYWORD, DOT, IDENTIFIER),
		  	          rhs(name, DOT, SUPERKEYWORD, DOT, IDENTIFIER)),
		  prod(method_invocation,
		  	          rhs(name, LPAREN, argument_list_opt, RPAREN),
		  	          rhs(primary, DOT, IDENTIFIER, LPAREN, argument_list_opt, RPAREN),
		  	          rhs(SUPERKEYWORD, DOT, IDENTIFIER, LPAREN, argument_list_opt, RPAREN),
		  	          rhs(name, DOT, SUPERKEYWORD, DOT, IDENTIFIER, LPAREN, argument_list_opt, RPAREN)),
		  prod(array_access,
		  	          rhs(name, LBRACK, expression, RBRACK),
		  	          rhs(primary_no_new_array, LBRACK, expression, RBRACK),
		  	          rhs(array_creation_init, LBRACK, expression, RBRACK)),
		  prod(postfix_expression,
		  	          rhs(primary),
		  	          rhs(name),
		  	          rhs(postincrement_expression),
		  	          rhs(postdecrement_expression)),
		  prod(postincrement_expression,
		  	          rhs(postfix_expression, PLUSPLUS)),
		  prod(postdecrement_expression,
		  	          rhs(postfix_expression, MINUSMINUS)),
		  prod(unary_expression,
		  	          rhs(preincrement_expression),
		  	          rhs(predecrement_expression),
		  	          rhs(PLUS, unary_expression),
		  	          rhs(MINUS, unary_expression),
		  	          rhs(unary_expression_not_plus_minus)), //300
		  prod(preincrement_expression,
		  	          rhs(PLUSPLUS, unary_expression)),
		  prod(predecrement_expression,
		  	          rhs(MINUSMINUS, unary_expression)),
		  prod(unary_expression_not_plus_minus,
		  	          rhs(postfix_expression),
		  	          rhs(COMP, unary_expression),
		  	          rhs(NOT, unary_expression),
		  	          rhs(cast_expression)),
		  prod(cast_expression,
		  	          rhs(LPAREN, primitive_type, dims_opt, RPAREN, unary_expression),
		  	          rhs(LPAREN, expression, RPAREN, unary_expression_not_plus_minus),
		  	          rhs(LPAREN, name, dims, RPAREN, unary_expression_not_plus_minus)),
		  prod(multiplicative_expression,
		  	          rhs(unary_expression),
		  	          rhs(multiplicative_expression, MULT, unary_expression),
		  	          rhs(multiplicative_expression, DIV, unary_expression),
		  	          rhs(multiplicative_expression, MOD, unary_expression)),
		  prod(additive_expression,
		  	          rhs(multiplicative_expression),
		  	          rhs(additive_expression, PLUS, multiplicative_expression),
		  	          rhs(additive_expression, MINUS, multiplicative_expression)),
		  prod(shift_expression,
		  	          rhs(additive_expression),
		  	          rhs(shift_expression, LSHIFT, additive_expression),
		  	          rhs(shift_expression, RSHIFT, additive_expression),
		  	          rhs(shift_expression, URSHIFT, additive_expression)),
		  prod(relational_expression,
		  	          rhs(shift_expression),
		  	          rhs(relational_expression, LT, shift_expression),
		  	          rhs(relational_expression, GT, shift_expression),
		  	          rhs(relational_expression, LTEQ, shift_expression),
		  	          rhs(relational_expression, GTEQ, shift_expression),
		  	          rhs(relational_expression, INSTANCEOF, reference_type)),
		  prod(equality_expression,
		  	          rhs(relational_expression),
		  	          rhs(equality_expression, EQEQ, relational_expression),
		  	          rhs(equality_expression, NOTEQ, relational_expression)),
		  prod(and_expression,
		  	          rhs(equality_expression),
		  	          rhs(and_expression, AND, equality_expression)),
		  prod(exclusive_or_expression,
		  	          rhs(and_expression),
		  	          rhs(exclusive_or_expression, XOR, and_expression)),
		  prod(inclusive_or_expression,
		  	          rhs(exclusive_or_expression),
		  	          rhs(inclusive_or_expression, OR, exclusive_or_expression)),
		  prod(conditional_and_expression,
		  	          rhs(inclusive_or_expression),
		  	          rhs(conditional_and_expression, ANDAND, inclusive_or_expression)),
		  prod(conditional_or_expression,
		  	          rhs(conditional_and_expression),
		  	          rhs(conditional_or_expression, OROR, conditional_and_expression)),
		  prod(conditional_expression,
		  	          rhs(conditional_or_expression),
		  	          rhs(conditional_or_expression, QUESTION, expression, 
		  	          	COLON, conditional_expression)),
		  prod(assignment_expression,
		  	          rhs(conditional_expression),
		  	          rhs(assignment)),
		  prod(assignment,
		  	          rhs(postfix_expression, assignment_operator, assignment_expression)),
		  prod(assignment_operator,
		  	          rhs(EQ),
		  	          rhs(MULTEQ),
		  	          rhs(DIVEQ),
		  	          rhs(MODEQ),
		  	          rhs(PLUSEQ),
		  	          rhs(MINUSEQ), //350
		  	          rhs(LSHIFTEQ),
		  	          rhs(RSHIFTEQ),
		  	          rhs(URSHIFTEQ),
		  	          rhs(ANDEQ),
		  	          rhs(XOREQ),
		  	          rhs(OREQ)),
		  prod(expression_opt,
		  	          rhs(expression),
		  	          rhs()),
		  prod(expression,
		  	          rhs(assignment_expression)),
		  prod(constant_expression,
		  	          rhs(expression))
		);		       
	}
	
	
	
	/**
	 * LR(1) must work.
	 */
	/**/ @Test /**/ public void testLR1()
	{
		try
		{
			new LR1Generator(this, Verbosity.Sparse).getParsingTable();
		}
		catch (GeneratorException ex)
		{
			ex.printStackTrace();
			fail();
		}
	}
	
	
	@Test public void testTime()
	{
		Time.measureTime(new SpecJava14());
	}
	
	
	public static void main(String[] args)
	{
		try
		{
			SpecJava14 spec = new SpecJava14();
			LRParsingTable table = new LR1Generator(spec, Verbosity.Detailled).getParsingTable();
			LRParsingTableDump.dumpToHTML(table, new File("SpecJava14.html")); //TEST
		}
		catch (GeneratorException ex)
		{
			ex.printStackTrace();
		}
	}

}